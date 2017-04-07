package ua.dominos.service;

import ua.dominos.dao.DominoesDao;
import ua.dominos.dao.impl.DominoesDaoImpl;
import ua.dominos.dao.transaction.Transaction;
import ua.dominos.dao.transaction.TransactionManager;
import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.exception.DominoesServiceException;

import java.util.*;

/**
 * Breadth first search algorithm implementation.
 */
public class DominoesServiceBFS implements DominoesService {
    private TransactionManager transactionManager = new TransactionManager();
    private DominoesDao dominoesDao = new DominoesDaoImpl();
    private List<DominoTile> dominoes;
    private final Map<Integer, Set<DominoTileChain>> chains = new HashMap<>();
    private final LinkedList<DominoTileChain> allCombinations = new LinkedList<>();

    @Override
    public List<DominoTileChain> getAllChainCombinations(List<DominoTile> dominoes) {
        initChains(dominoes);
        for (Integer chainSize = 1; chainSize <= dominoes.size(); chainSize++) {
            Set<DominoTileChain> currentChainSet = chains.get(chainSize);
            Set<DominoTileChain> nextChainSet = new HashSet<>();
            for (DominoTileChain dominoTileChain : currentChainSet) {
                nextChainSet.addAll(findNextChains(dominoTileChain));
            }
            allCombinations.addAll(nextChainSet);
            chains.remove(chainSize - 1);
            chains.put(chainSize + 1, nextChainSet);
        }
        return this.allCombinations;
    }

    @Override
    public DominoTileChain getLongestCombination(List<DominoTile> dominoes) {
        if (allCombinations.isEmpty()) {
            getAllChainCombinations(dominoes);
        }
        return this.allCombinations.getLast();
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

    private Set<DominoTileChain> findNextChains(DominoTileChain currentChain) {
        Set<DominoTileChain> nextChainSet = new HashSet<>();
        for (DominoTile domino : dominoes) {
            if (!currentChain.getChain().contains(domino)) {
                DominoTileChain nextChain = currentChain.connect(domino);
                if (nextChain.length() > currentChain.length()) {
                    nextChainSet.add(nextChain);
                }
            }
        }
        return nextChainSet;
    }

    private void initChains(List<DominoTile> dominoes) {
        this.dominoes = dominoes;
        Set<DominoTileChain> initialChains = new HashSet<>();
        for (DominoTile domino : dominoes) {
            DominoTileChain chain = new DominoTileChain();
            chain = chain.connect(domino);
            initialChains.add(chain);
        }
        chains.put(1, initialChains);
    }
}