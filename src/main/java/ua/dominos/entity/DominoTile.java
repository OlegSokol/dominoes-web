package ua.dominos.entity;

import java.util.Objects;

/**
 * Domino entity
 */
public class DominoTile {
    private int index;
    private Integer valueLeft;
    private Integer valueRight;
    private boolean isSwap = false;

    /**
     * Creates domino entity with.
     * Values should be more than MIN_VALUE and less than MAX_VALUE.
     *
     * @param valueLeft
     * @param valueRight
     */
    public DominoTile(Integer valueLeft, Integer valueRight, int index) {
        this.valueLeft = valueLeft;
        this.valueRight = valueRight;
        this.index = index;
    }

    public Integer getValueLeft() {
        return valueLeft;
    }

    public Integer getValueRight() {
        return valueRight;
    }

    public DominoTile swap() {
        DominoTile dominoTile = new DominoTile(valueRight, valueLeft, index);
        dominoTile.setSwap(true);
        return dominoTile;
    }

    public int getIndex() {
        return index;
    }

    public boolean isSwap() {
        return isSwap;
    }

    public void setSwap(boolean swap) {
        isSwap = swap;
    }

    @Override
    public String toString() {
        return "[" + valueLeft + "|" + valueRight + ']';
    }

    @Override
    synchronized public int hashCode() {
        return Objects.hashCode(this.valueLeft) + Objects.hashCode(this.valueRight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DominoTile other = (DominoTile) obj;
        return (this.valueLeft.equals(other.valueLeft) && this.valueRight.equals(other.valueRight))
                || (this.valueRight.equals(other.valueLeft) && this.valueLeft.equals(other.valueRight));
    }

}
