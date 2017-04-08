package ua.dominos.service;

import org.junit.Test;
import ua.dominos.entity.DominoBox;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.exception.DominoesServiceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DominoesServiceIntegrationTest {
    private DominoesService service = new DominoesServiceBFS();
    private DominoBox dominoBox = DominoBox.getInstance();

    @Test
    public void shouldGetAllDominoesTile() throws DominoesServiceException {
        assertTrue(service.getAllDominoesTile().size() == 28);
    }

    @Test
    public void shouldSaveChainsHistory() throws DominoesServiceException {
        List<DominoTileChain> dominoTileChainList = service.getAllChainCombinations(dominoBox.getRandomTiles(4));
        assertTrue(service.saveChains(dominoTileChainList));
    }

}