package ua.dominos.service;

import org.junit.Test;
import ua.dominos.exception.DominoesServiceException;

import static org.junit.Assert.*;

public class DominoesServiceTest {
    private DominoesService service = new DominoesServiceBFS();

    @Test
    public void shouldGetAllDominoesTile() throws DominoesServiceException {
        assertTrue(service.getAllDominoesTile().size() == 28);
    }

}