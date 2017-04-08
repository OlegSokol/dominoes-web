package ua.dominos.dao.impl;

import org.apache.log4j.Logger;
import ua.dominos.dao.ConnectionPool;
import ua.dominos.dao.DominoesDao;
import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.dominos.dao.Query.*;

public class DominoesDaoImpl implements DominoesDao {
    private static final Logger LOG = Logger.getLogger(DominoesDaoImpl.class);

    @Override
    public boolean saveChains(List<DominoTileChain> chains) {
        Connection connection = ConnectionPool.getConnection();
        try {
            for (DominoTileChain chain : chains) {
                int chainGeneratedId;
                List<DominoTile> dominoChain = chain.getChain();
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHAIN, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setInt(1, chain.length());
                    preparedStatement.executeUpdate();
                    chainGeneratedId = getGeneratedId(preparedStatement);
                }
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOMINOES_CHAINS)) {
                    for (DominoTile dominoTile : dominoChain) {
                        preparedStatement.setInt(1, chainGeneratedId);
                        preparedStatement.setInt(2, dominoTile.getIndex());
                        preparedStatement.setInt(3, dominoChain.indexOf(dominoTile) + 1);
                        preparedStatement.setBoolean(4, dominoTile.isSwap());
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                }
            }
        } catch (SQLException e) {
            LOG.warn("Can not save chains", e);
            return false;
        }
        return true;
    }

    @Override
    public List<DominoTileChain> getChainsHistory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<DominoTile> getAllDominoesTile() {
        List<DominoTile> dominoTiles = new ArrayList<>();
        Connection connection = ConnectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_DOMINO_TILES);
            while (resultSet.next()) {
                dominoTiles.add(fromResultSetToDominoTileConverter(resultSet));
            }
        } catch (SQLException e) {
            LOG.warn("Can not get all dominoes", e);
        }
        return dominoTiles;
    }

    private int getGeneratedId(Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating chain failed, no ID obtained.");
        }
    }

    private DominoTile fromResultSetToDominoTileConverter(ResultSet resultSet) throws SQLException {
        int index = resultSet.getInt("index_val");
        int leftValue = resultSet.getInt("left_value");
        int rightValue = resultSet.getInt("right_value");
        return new DominoTile(leftValue, rightValue, index);
    }
}
