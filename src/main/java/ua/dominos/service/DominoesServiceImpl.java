package ua.dominos.service;

import ua.dominos.dao.DominoesDao;
import ua.dominos.dao.transaction.TransactionManager;
import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.service.exception.DominoesServiceException;
import ua.dominos.util.CombinationsUtil;

import java.util.List;

/**
 * Breadth first search algorithm implementation.
 */
public class DominoesServiceImpl implements DominoesService {
    private TransactionManager transactionManager;
    private DominoesDao dominoesDao;
    private CombinationsUtil combinationsUtil;

    public DominoesServiceImpl(TransactionManager transactionManager, DominoesDao dominoesDao, CombinationsUtil combinationsUtil) {
        this.transactionManager = transactionManager;
        this.dominoesDao = dominoesDao;
        this.combinationsUtil = combinationsUtil;
    }

    @Override
    public List<DominoTileChain> getAllChainCombinations(List<DominoTile> dominoes) {
        return this.combinationsUtil.getAllChainCombinations(dominoes);
    }

    @Override
    public DominoTileChain getLongestCombination(List<DominoTile> dominoes) {
        return this.combinationsUtil.getLongestCombination(dominoes);
    }

    @Override
    public boolean saveChains(List<DominoTileChain> chains) throws DominoesServiceException {
        return transactionManager.execute(() -> dominoesDao.saveChains(chains));
    }

    @Override
    public List<DominoTileChain> getChainsHistory() throws DominoesServiceException {
        return transactionManager.executeWithoutTransaction(() -> dominoesDao.getChainsHistory());
    }

    @Override
    public List<DominoTile> getAllDominoesTile() throws DominoesServiceException {
        return transactionManager.executeWithoutTransaction(() -> dominoesDao.getAllDominoesTile());
    }
}