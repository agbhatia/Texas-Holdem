package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class ThreeOfAKindEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.THREE_OF_A_KIND;

    public ThreeOfAKindEvaluator() {
        super();
    }

    public HandEvalResult evaluate(Card[] cards) {
        // To calculate the score we get the value that is repeated 3 times, and the 2 highest kickers. Then we
        // weigh each accordingly (the value repeated 3 times is obviously weighted the most).
        int[] scores = new int[3];

        // First find the highest number that repeats exactly 3 times. We don't worry about catching the case
        // where it repeats more than three times, because that will already be caught by the fourofakind
        // evaluator.
        int result = this.findHighestDuplicate(cards, 3);
        int finalScore = 0;
        boolean isMatch = result > -1;
        if (isMatch) {
            scores[0] = result;
            // We increment the index to the new position and look for our two highest kickers to go along
            // with the repeating card. We traverse cards which is already in order from highest to lowest.
            int i = 1;
            for (Card card : cards) {
                int rankValue = card.rankToInt();
                if (rankValue != result) {
                    scores[i] = card.rankToInt();
                    i++;
                    if (i > scores.length-1) {
                        break;
                    }
                }
            }
            finalScore = this.calculateScore(scores);
        }

        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
