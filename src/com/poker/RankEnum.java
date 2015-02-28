package com.poker;

/**
 * Created by atul on 2/27/15.
 */
public enum RankEnum {
    // We want to traverse enums in order from highest ranking to lowest. Enum order is preserved by
    // order of listing them.
    ACE(12, "A"),
    KING(11, "K"),
    QUEEN(10, "Q"),
    JACK(9, "J"),
    TEN(8, "10"),
    NINE(7, "9"),
    EIGHT(6, "8"),
    SEVEN(5, "7"),
    SIX(4, "6"),
    FIVE(3, "5"),
    FOUR(2, "4"),
    THREE(1, "3"),
    TWO(0, "2");

    private String rankString;
    private int value;

    RankEnum(int value, String rankString) {
        this.value = value;
        this.rankString = rankString;
    }

    @Override
    public String toString() {
        return rankString;
    }

    public int getValue() {
        return value;
    }

    public static int numRanks() {
        return RankEnum.values().length;
    }

    public boolean equalsRank(RankEnum other) {
        return other.getValue() == this.getValue();
    }

    public boolean isConsecutive(RankEnum other) {
        return (this.getValue() - other.getValue() == 1) || (other.getValue() - this.getValue() == 1);
    }
}

