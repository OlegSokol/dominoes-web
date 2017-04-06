package ua.dominos.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DominoBoxTest {
    private DominoBox dominoBox;

    @Before
    public void before() {
        dominoBox = DominoBox.getInstance();
    }

    @Test
    public void shouldGetRandomDominoes() {
        assertTrue(dominoBox.getRandomTiles(3).size() == 3);
    }

}