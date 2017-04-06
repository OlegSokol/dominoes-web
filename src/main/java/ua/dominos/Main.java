package ua.dominos;

import ua.dominos.entity.DominoBox;
import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.service.DominoesServiceBFS;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DominoBox dominoBox = DominoBox.getInstance();
        List<DominoTile> randomTiles = dominoBox.getRandomTiles(5);
        System.out.println("input - " + randomTiles);
        DominoesServiceBFS dominoesServiceBFS = new DominoesServiceBFS();
        List<DominoTileChain> dominoTileChains = dominoesServiceBFS.getAllChainCombinations(randomTiles);
        for (DominoTileChain allCombination : dominoTileChains ) {
            System.out.println(allCombination);
        }
    }
}
