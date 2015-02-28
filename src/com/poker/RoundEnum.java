package com.poker;

/**
 * Created by atul on 2/24/15.
 */
public enum RoundEnum {
    FLOP_ROUND(3, 1, "Flop"),
    TURN_ROUND(1, 1, "Turn"),
    RIVER_ROUND(1, 1, "River");


    private int numCards;
    private int cardsToBurn;
    private String name;

    RoundEnum(int numCards, int cardsToBurn, String name) {
        this.numCards = numCards;
        this.cardsToBurn = cardsToBurn;
        this.name = name;
    }

    public int getNumCards() {
        return numCards;
    }

    public int getCardsToBurn() {
        return cardsToBurn;
    }

    @Override
    public String toString() {
        return name;
    }
}

