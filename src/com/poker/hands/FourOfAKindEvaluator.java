package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class FourOfAKindEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.FOUR_OF_A_KIND;

    public FourOfAKindEvaluator() {
        super();
    }

    public HandEvalResult evaluate(Card[] cards) {
        // To calculate the score we get the value that is repeated 4 times, and the highest kicker. Then we
        // weigh each accordingly (the value repeated 4 times is obviously weighted the most).
        int[] scores = new int[2];

        // First find the highest number that repeats exactly 4 times.
        int result = this.findHighestDuplicate(cards, 4);
        int finalScore = 0;
        boolean isMatch = result > -1;
        if (isMatch) {
            scores[0] = result;
            // We increment the index to the new position and look for our highest kicker to go along
            // with the repeating card. We traverse cards which is already in order from highest to lowest.
            int i = 1;
            for (Card card : cards) {
                int rankValue = card.rankToInt();
                if (rankValue != result) {
                    scores[i] = card.rankToInt();
                    i++;
                    if (i >= scores.length) {
                        break;
                    }
                }
            }
            finalScore = this.calculateScore(scores);
        }

        return new HandEvalResult(isMatch, handType, finalScore);
    }

}
