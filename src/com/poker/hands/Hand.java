package com.poker.hands;

import com.poker.Card;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by atul on 2/25/15.
 * This class encapsulates the cards in a hand. We sort the cards in rank order starting from the highest, so that
 * we can easily traverse forward and know that when we match something for the first time, we found the highest
 * ranked card that matched.
 */
public class Hand implements Comparable<Hand> {
    private static final int NUM_CARDS = 7;
    private List<Card> cards = new ArrayList<>(NUM_CARDS);
    private HandEvalResult handEvalResult;

    // This is the list of evaluators that the hand needs to be run through. It is ordered such that
    // as soon as we find a match, we know that is the highest valued hand we have.
    private static final List<HandEvaluator> handEvaluators = new ArrayList<HandEvaluator>() {{
        add(new StraightFlushEvaluator());
        add(new FourOfAKindEvaluator());
        add(new FullHouseEvaluator());
        add(new FlushEvaluator());
        add(new StraightEvaluator());
        add(new ThreeOfAKindEvaluator());
        add(new TwoPairEvaluator());
        add(new OnePairEvaluator());
        add(new HighCardEvaluator());
    }};

    public Hand(Card[] playerCards, List<Card> tableCards) {
        // The player cards and table cards should add up to 7.
        if ((playerCards.length + tableCards.size()) != NUM_CARDS) {
            throw new IllegalArgumentException("Invalid number of cards passed in");
        }

        int i;
        for (i = 0; i < playerCards.length; i++) {
            cards.add(playerCards[i]);
        }

        for (Card tableCard : tableCards) {
            cards.add(tableCard);
        }

        // Sort the cards
        Collections.sort(cards);
    }

    public Hand(List<Card> cards) {
        if (cards.size() != NUM_CARDS) {
            throw new IllegalArgumentException("Invalid number of cards passed in");
        }

        this.cards = cards;
        Collections.sort(cards);
    }

    /**
     * Run through all the evaluators and find the first / highest matching hand type.
     * @return
     */
    public HandEvalResult evaluate() {
        handEvalResult = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            HandEvalResult result = handEvaluator.evaluate(cards);
            if (result.isMatch()) {
                handEvalResult = result;
                break;
            }
        }
        return handEvalResult;
    }

    public HandEvalResult getHandEvalResult() {
        return handEvalResult;
    }

    @Override
    public int compareTo(Hand otherHand) {
        HandEvalResult thisResult = this.getHandEvalResult();
        HandEvalResult otherResult = otherHand.getHandEvalResult();

        return thisResult.compareTo(otherResult);
    }
}
