package ua.dominos.service;

import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.service.exception.DominoesServiceException;

import java.util.List;

public interface DominoesService {
    List<DominoTileChain> getAllChainCombinations(List<DominoTile> dominoes);

    DominoTileChain getLongestCombination(List<DominoTile> dominoes);

    boolean saveChains(List<DominoTileChain> chains) throws DominoesServiceException;

    List<DominoTileChain> getChainsHistory() throws DominoesServiceException;

    List<DominoTile> getAllDominoesTile() throws DominoesServiceException;
}
