package ua.dominos.service;

import org.junit.Test;
import ua.dominos.entity.DominoBox;
import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.util.CombinationsUtil;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DominoesServiceTest {
    private CombinationsUtil combinationsUtil = new CombinationsUtil();
    private DominoesService service = new DominoesServiceImpl(null, null, combinationsUtil);
    private List<DominoTile> randomTiles = DominoBox.getInstance().getRandomTiles(5);

    @Test
    public void shouldGetAllChainCombinations() throws Exception {
        List<DominoTileChain> allChainCombinations = service.getAllChainCombinations(randomTiles);
        assertTrue(allChainCombinations.size() > 0);
    }

    @Test
    public void shouldGetLongestCombination() throws Exception {
        DominoTileChain longestCombination = service.getLongestCombination(randomTiles);
        assertTrue(longestCombination != null);
    }
}