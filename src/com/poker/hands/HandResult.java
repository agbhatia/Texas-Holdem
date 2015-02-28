package com.poker.hands;

/**
 * Created by atul on 2/25/15.
 */
public enum HandResult {
    HIGH_CARD("High Card", 0),
    ONE_PAIR("1 Pair", 1),
    TWO_PAIR("2 Pairs", 2),
    THREE_OF_A_KIND("3 of a Kind", 3),
    STRAIGHT("Straight", 3),
    FLUSH("Flush", 5),
    FULL_HOUSE("Full House", 6),
    FOUR_OF_A_KIND("Four of a Kind", 7),
    STRAIGHT_FLUSH("Straight Flush", 8),
    ROYAL_FLUSH("Royal Flush", 9);

    private String description;
    private int value;

    HandResult(String description, int value) {
        this.description = description;
        this.value = value;
    }

    @Override
    public String toString() {
        return description;
    }

    public int getValue() {
        return value;
    }
}
