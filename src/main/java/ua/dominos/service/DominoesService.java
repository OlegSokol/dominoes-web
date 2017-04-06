package ua.dominos.service;

import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;

import java.util.List;

public interface DominoesService {
    List<DominoTileChain> getAllChainCombinations(List<DominoTile> dominoes);

    DominoTileChain getLongestCombination(List<DominoTile> dominoes);
}
