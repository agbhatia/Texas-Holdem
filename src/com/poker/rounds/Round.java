package com.poker.rounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/24/15.
 * this class is just a way for us to define rounds (by number of cards to burn and number of cards to deal).
 */

public abstract class Round {
    public static final Round FLOP = new FlopRound();
    public static final Round RIVER = new RiverRound();
    public static final Round TURN = new TurnRound();

    private final RoundEnum roundEnum;
    private final int numCards;
    private final int cardsToBurn;
    private final String name;

    public Round(RoundEnum roundEnum, String name, int numCards, int cardsToBurn) {
        this.roundEnum = roundEnum;
        this.numCards = numCards;
        this.cardsToBurn = cardsToBurn;
        this.name = name;
    }

    public static List<Round> getHoldemRounds() {
        List<Round> rounds = new ArrayList<Round>();
        rounds.add(FLOP);
        rounds.add(TURN);
        rounds.add(RIVER);
        return rounds;
    }

    public int getNumCards() {
        return numCards;
    }

    public int getCardsToBurn() {
        return cardsToBurn;
    }

    public String toString() {
        return name;
    }

}