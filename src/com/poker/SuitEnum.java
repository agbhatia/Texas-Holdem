package com.poker;

/**
 * Created by atul on 2/27/15.
 */
public enum SuitEnum {
    DIAMONDS(0, "d"),
    CLUBS(1, "c"),
    HEARTS(2, "h"),
    SPADES(3, "s");

    private int value;
    private String suitString;

    SuitEnum(int value, String suitString) {
        this.value = value;
        this.suitString = suitString;
    }

    @Override
    public String toString() {
        return suitString;
    }

    public int getValue() {
        return value;
    }

    public static int numSuits() {
        return SuitEnum.values().length;
    }

    public boolean equalsSuit(SuitEnum other) {
        return other.getValue() == this.getValue();
    }
}
