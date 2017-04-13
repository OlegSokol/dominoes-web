package ua.dominos.util;

import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;

import java.util.*;

/**
 * Breadth first search algorithm implementation.
 */
public class CombinationsUtil {
    private List<DominoTile> dominoes;
    private final Map<Integer, Set<DominoTileChain>> chains = new HashMap<>();
    private final LinkedList<DominoTileChain> allCombinations = new LinkedList<>();

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
        List<DominoTileChain> combinations = new LinkedList<>();
        combinations.addAll(this.allCombinations);
        allCombinations.clear();
        return combinations;
    }

    public DominoTileChain getLongestCombination(List<DominoTile> dominoes) {
        if (allCombinations.isEmpty()) {
            getAllChainCombinations(dominoes);
        }
        return this.allCombinations.getLast();
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
