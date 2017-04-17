package ua.dominos.service;

import org.junit.Test;
import ua.dominos.dao.DominoesDao;
import ua.dominos.dao.impl.DominoesDaoImpl;
import ua.dominos.dao.transaction.TransactionManager;
import ua.dominos.entity.DominoBox;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.service.exception.DominoesServiceException;
import ua.dominos.util.CombinationsUtil;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DominoesServiceIntegrationTest {
    private TransactionManager transactionManager = new TransactionManager();
    private DominoesDao dominoesDao = new DominoesDaoImpl();
    private CombinationsUtil combinationsUtil = new CombinationsUtil();
    private DominoesService service = new DominoesServiceImpl(transactionManager, dominoesDao, combinationsUtil);
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

    @Test
    public void shouldGetChainsHistory() throws DominoesServiceException {
        List<DominoTileChain> dominoTileChainList = service.getAllChainCombinations(dominoBox.getRandomTiles(4));
        service.saveChains(dominoTileChainList);
        List<DominoTileChain> chainsHistory = service.getChainsHistory();
        assertTrue(chainsHistory.size() > 0);
    }

}