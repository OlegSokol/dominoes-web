package ua.dominos.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Random dominoes generator
 */
public class DominoBox {
    private static final Integer MIN_VALUE = 0;
    private static final Integer MAX_VALUE = 6;

    private final List<DominoTile> dominoes = new ArrayList<>();
    private static DominoBox instance = null;
    
    private void init() {
        for (int value1 = MIN_VALUE; value1 <= MAX_VALUE; value1++) {
            for (int value2 = MIN_VALUE; value2 <= value1; value2++) {
                    DominoTile dominoTile = new DominoTile(value1, value2);
                    dominoes.add(dominoTile);
            }
        }
    }

    private DominoBox() {
        init();
    }
    
    /**
     * Singleton pattern
     */
    public static DominoBox getInstance() {
        if (instance == null) {
            instance = new DominoBox();
        } 
        return instance;
    }
    
    /**
     * Get list of random dominoe tiles
     * @param number of random tiles
     *
     */
    public List<DominoTile> getRandomTiles(Integer number) {
        List<DominoTile> copy = new LinkedList<DominoTile>(dominoes);
        Collections.shuffle(copy);
        return copy.subList(0, number);
    }
}
