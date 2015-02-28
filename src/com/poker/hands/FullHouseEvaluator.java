package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/25/15.
 */
public class FullHouseEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.FULL_HOUSE;

    public FullHouseEvaluator() {
        super();
    }

    public HandEvalResult evaluate(List<Card> cards) {
        List<RankEnum> scores = new ArrayList<>();
        int finalScore = 0;

        // First find the highest number that repeats exactly 3 times. We don't worry about catching the case
        // where it repeats more than three times, because that will already be caught by the fourofakind
        // evaluator.
        RankEnum result = this.findHighestDuplicate(cards, 3);
        boolean isMatch = (result != null);

        if (isMatch) {
            // Now that we found a set of 3, we need to find a pair to go along with it to complete the full
            // house. If we find it, then we have a full house.
            List<RankEnum> pairs = this.findTopNPairs(cards, 1);
            isMatch = pairs.size() >= 1;
            if (isMatch) {
                // Make sure that the three of a kind is weighted more.
                scores.add(result);
                scores.add(pairs.get(0));
                finalScore = calculateScore(scores);
            }
        }
        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
