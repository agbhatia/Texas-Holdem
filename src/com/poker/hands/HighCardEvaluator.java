package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class HighCardEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.HIGH_CARD;

    public HighCardEvaluator() {
        super();
    }

    public HandEvalResult evaluate(Card[] cards) {
        int[] scores = new int[5];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = cards[i].rankToInt();
        }
        return new HandEvalResult(true, handType, calculateScore(scores));
    }
}
