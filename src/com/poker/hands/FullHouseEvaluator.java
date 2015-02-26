package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class FullHouseEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.FULL_HOUSE;

    public FullHouseEvaluator() {
        super();
    }

    public HandEvalResult evaluate(Card[] cards) {
        int[] scores = new int[4];
        int finalScore = 0;

        // First find the highest number that repeats exactly 3 times. We don't worry about catching the case
        // where it repeats more than three times, because that will already be caught by the fourofakind
        // evaluator.
        int result = this.findHighestDuplicate(cards, 3);

        boolean isMatch = (result > -1);

        if (isMatch) {
            // Now that we found a set of 3, we need to find a pair to go along with it to complete the full
            // house. If we find it, then we have a full house.
            Pair pairs = this.findTopNPairs(cards, 1);
            isMatch = pairs.foundAllPairs;
            if (isMatch) {
                // Make sure that the three of a kind is weighted more.
                scores[0] = result;
                scores[1] = pairs.pairs[0];
                finalScore = calculateScore(scores);
            }
        }
        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
