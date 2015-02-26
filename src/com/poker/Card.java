package com.poker;

import java.util.Comparator;

/**
 * Created by atul on 2/24/15.
 * The Card class defines the layout of a Card. It includes a CardRank, an enum of the different card values,
 * and a CardSuit an enum of the different suits of cards.
 */
public class Card implements Comparable<Card>{

    private CardRank rank;
    private CardSuit suit;

    public enum CardRank {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    /**
     * This String array is used to map an enum to a string representation of the rank.
     */
    public static final String[] RANK_STRING = {
            "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"
    };

    public enum CardSuit {
        DIAMONDS,
        CLUBS,
        HEARTS,
        SPADES
    }

    /**
     * The suit symbols (use the suit enum to map into this).
     */
    public static final char[] SUIT_STRING = { 'd', 'c', 'h', 's' };

    /**
     *
     * @param rank: rankEnum
     * @param suit: suitEnum
     */
    public Card(CardRank rank, CardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public CardRank getRank() {
        return rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public String toString() {
        return String.format("%s%s", RANK_STRING[rank.ordinal()], SUIT_STRING[suit.ordinal()]);
    }

    public int rankToInt() {
        return rank.ordinal();
    }

    public int suitToInt() {
        return suit.ordinal();
    }

    @Override
    public int hashCode() {
        return ((rankToInt() * CardSuit.values().length) + suitToInt());
    }

    public int codeBySuit() {
        return ((suitToInt() * CardRank.values().length) + rankToInt());
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
        if (obj instanceof Card) {
            return ((Card) obj).hashCode() == hashCode();
        } else {
            return false;
        }
    }
}
