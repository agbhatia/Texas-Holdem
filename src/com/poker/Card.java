package com.poker;

import java.util.Comparator;

/**
 * Created by atul on 2/24/15.
 * The Card class defines the layout of a Card. It includes a CardRank, an enum of the different card values,
 * and a CardSuit an enum of the different suits of cards.
 */
public class Card implements Comparable<Card>{

    private RankEnum rank;
    private SuitEnum suit;

    /**
     *
     * @param rank: rankEnum
     * @param suit: suitEnum
     */
    public Card(RankEnum rank, SuitEnum suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public RankEnum getRank() {
        return rank;
    }

    public SuitEnum getSuit() {
        return suit;
    }

    public String toString() {
        return rank.toString() + suit.toString();
    }

    public int rankToInt() {
        return rank.getValue();
    }

    public int suitToInt() {
        return suit.getValue();
    }

    @Override
    public int hashCode() {
        return ((rankToInt() * SuitEnum.numSuits()) + suitToInt());
    }


    @Override
    public int compareTo(Card card) {
        int myHash = this.hashCode();
        int otherHash = card.hashCode();

        return  myHash < otherHash ? +1 : myHash > otherHash ? -1 : 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Card) && (obj.hashCode() == hashCode());
    }
}
