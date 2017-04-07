package ua.dominos.entity;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Chains of dominoes. Immutable.
 */
public class DominoTileChain {
    private final LinkedList<DominoTile> chain = new LinkedList<>();

    /**
     * Add element to chain
     * @param dominoTile new element
     * @return new chain if element was connected, otherwise returns this.
     */
    public DominoTileChain connect(DominoTile dominoTile) {
        DominoTileChain result = copy();

        if (result.chain.isEmpty()) {
            result.chain.add(dominoTile);
            return result;
        }
        if (dominoTile.getValueRight().equals(getValueLeft())) {
            result.chain.addFirst(dominoTile);
            return result;
        }
        if (dominoTile.getValueLeft().equals(getValueRight())) {
            result.chain.addLast(dominoTile);
            return result;
        }
        if (dominoTile.getValueLeft().equals(getValueLeft())) {
            result.chain.addFirst(dominoTile.swap());
            return result;
        }
        if (dominoTile.getValueRight().equals(getValueRight())) {
            result.chain.addLast(dominoTile.swap());
            return result;
        }
        return this;
    }

    private Integer getValueLeft() {
        if (!chain.isEmpty())
            return chain.getFirst().getValueLeft();
        return null;
    }

    private Integer getValueRight() {
        if (!chain.isEmpty())
            return chain.getLast().getValueRight();
        return null;
    }

    public Integer length() {
        return chain.size();
    }

    private DominoTileChain copy() {
        DominoTileChain result = new DominoTileChain();
        result.chain.addAll(chain);
        return result;
    }

    public LinkedList<DominoTile> getChain() {
        return chain;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (DominoTile dominoTile : chain) {
            result.append(dominoTile.toString());
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (DominoTile dominoTail : chain) {
            hash += hash * dominoTail.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DominoTileChain other = (DominoTileChain) obj;
        Boolean isAllTilesEqual = true;
        for (DominoTile tile : chain) {
            isAllTilesEqual &= other.chain.contains(tile);
        }
        return isAllTilesEqual && Objects.equals(this.length(), other.length());
    }
}
