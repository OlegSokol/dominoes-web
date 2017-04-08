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
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CHAINS, Statement.RETURN_GENERATED_KEYS)) {
            for (DominoTileChain chain : chains) {
                List<DominoTile> dominoes = chain.getChain();
                preparedStatement.setInt(1, chain.length());
                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
                int chainGeneratedId = getGeneratedId(preparedStatement);
                saveDominoPositions(connection, chainGeneratedId, dominoes);
            }
        } catch (SQLException e) {
            LOG.warn("Can not save chains", e);
            return false;
        }
        return true;
    }

    private void saveDominoPositions(Connection connection, int chainGeneratedId, List<DominoTile> dominoes) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_DOMINOES_CHAINS)) {
            for (DominoTile dominoTile : dominoes) {
                preparedStatement.setInt(1, chainGeneratedId);
                preparedStatement.setInt(2, dominoTile.getIndex());
                preparedStatement.setBoolean(3, dominoTile.isSwap());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Override
    public List<DominoTileChain> getChainsHistory() {
        Connection connection = ConnectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_DOMINOES_CHAINS);
            return fromResultSetToDominoTileChainList(resultSet);
        } catch (SQLException e) {
            LOG.warn("Can not get history of chains", e);
            return null;
        }
    }

    @Override
    public List<DominoTile> getAllDominoesTile() {
        Connection connection = ConnectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            List<DominoTile> dominoTiles = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_DOMINO_TILES);
            while (resultSet.next()) {
                dominoTiles.add(fromResultSetToDominoTileConverter(resultSet));
            }
            return dominoTiles;
        } catch (SQLException e) {
            LOG.warn("Can not get all dominoes", e);
            return null;
        }
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
           return newDomino.swap();
        }
        return newDomino;
    }
}
