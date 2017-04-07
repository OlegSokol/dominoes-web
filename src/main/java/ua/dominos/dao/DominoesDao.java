package ua.dominos.dao;

import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;

import java.sql.Connection;
import java.util.List;

public interface DominoesDao {
    boolean saveChains(List<DominoTileChain> chains);

    List<DominoTileChain> getChainsHistory();

    List<DominoTile> getAllDominoesTile();
}
