package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/25/15.
 */
public class HighCardEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.HIGH_CARD;
    private static int numRanksNeeded = 5;

    public HighCardEvaluator() {
        super();
    }

    public HandEvalResult evaluate(List<Card> cards) {
        List<RankEnum> scores = new ArrayList<>();
        for (int i = 0; i < numRanksNeeded; i++) {
            scores.add(cards.get(i).getRank());
        }

        // We know high card will always return true
        return new HandEvalResult(true, handType, calculateScore(scores));
    }
}
