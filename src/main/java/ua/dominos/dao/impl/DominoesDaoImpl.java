package ua.dominos.dao.impl;

import org.apache.log4j.Logger;
import ua.dominos.dao.ConnectionPool;
import ua.dominos.dao.DominoesDao;
import ua.dominos.entity.DominoBox;
import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;

import java.sql.*;
import java.util.*;

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
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CHAINS, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setInt(1, chain.length());
                    preparedStatement.executeUpdate();
                    chainGeneratedId = getGeneratedId(preparedStatement);
                }
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_DOMINOES_CHAINS)) {
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
        Connection connection = ConnectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_DOMINOES_CHAINS);
            return fromResultSetToDominoTileChainList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DominoTile> getAllDominoesTile() {
        List<DominoTile> dominoTiles = new ArrayList<>();
        Connection connection = ConnectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_DOMINO_TILES);
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

    private List<DominoTileChain> fromResultSetToDominoTileChainList(ResultSet resultSet) throws SQLException {
        Map<Integer, DominoTileChain> dominoTileChainMap = new LinkedHashMap<>();
        DominoBox dominoBox = DominoBox.getInstance();
        while (resultSet.next()) {
            int dominoId = resultSet.getInt("domino_id");
            int chainId = resultSet.getInt("chain_id");
            boolean isSwap = resultSet.getBoolean("swap");
            if (dominoTileChainMap.containsKey(chainId)) {
                DominoTileChain dominoTileChain = dominoTileChainMap.get(chainId);
                dominoTileChain.getChain().add(getDominoTile(dominoId, isSwap, dominoBox));
            } else {
                DominoTileChain dominoTileChain = new DominoTileChain();
                dominoTileChain.getChain().add(getDominoTile(dominoId, isSwap, dominoBox));
                dominoTileChainMap.put(chainId, dominoTileChain);
            }
        }
        return new LinkedList<>(dominoTileChainMap.values());
    }

    private DominoTile getDominoTile(int index, boolean swap, DominoBox dominoBox) {
        DominoTile domino = dominoBox.getByIndex(index);
        DominoTile newDomino = new DominoTile(domino.getValueLeft(), domino.getValueRight(), index);
        if (swap) {
            newDomino.swap();
        }
        return newDomino;
    }
}
