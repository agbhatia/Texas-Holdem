package com.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by atul on 2/24/15.
 * Representation of a deck of cards.
 */
public class Deck {

    // Use list instead of array for flexibility (no real performance impact).
    private List<Card> cards;
    private int indexForNextCard;

    // This random num generator is used to retrieve the next card in the deck.
    // It is not necessary to actually keep the cards in a certain order / even shuffle
    // the cards, since we can randomly just retrieve a given index in the cards list.
    private Random randNumGenerator;

    public Deck() {
        // Since
        this.cards = new ArrayList<Card>();

        // Go through our list of ranks. For each rank, create a card
        // for every suit in our suit enum. Add each combination of
        // rank + suit into our list of cards.
        for (RankEnum rank : RankEnum.values()) {
            for (SuitEnum suit : SuitEnum.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        this.randNumGenerator = new Random();
        this.indexForNextCard = 0;
    }

    /**
     * This function shuffles the order of the cards in our list of cards.
     * Since we are also using a random number generator to retrieve a list
     * of cards, this function isn't super necessary. However, given that
     * the rng will be pseudo-random, we will add this here to try and increase
     * the randomness.
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
        indexForNextCard = 0;
    }

    public void reset() {
        indexForNextCard = 0;
    }

    public Card deal() {
        if (indexForNextCard >= this.cards.size()) {
            throw new IllegalArgumentException("No more cards to deal");
        }
        return cards.get(indexForNextCard++);
    }
}
