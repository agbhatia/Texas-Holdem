package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/25/15.
 */
public class ThreeOfAKindEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.THREE_OF_A_KIND;
    private static final int numRanksNeeded = 3;


    public ThreeOfAKindEvaluator() {
        super();
    }

    public HandEvalResult evaluate(List<Card> cards) {
        // To calculate the score we get the value that is repeated 3 times, and the 2 highest kickers. Then we
        // weigh each accordingly (the value repeated 3 times is obviously weighted the most).
        List<RankEnum> scores = new ArrayList<>();

        // First find the highest number that repeats exactly 3 times. We don't worry about catching the case
        // where it repeats more than three times, because that will already be caught by the fourofakind
        // evaluator.
        RankEnum result = this.findHighestDuplicate(cards, 3);
        int finalScore = 0;
        boolean isMatch = result != null;
        if (isMatch) {
            scores.add(result);
            // We increment the index to the new position and look for our two highest kickers to go along
            // with the repeating card. We traverse cards which is already in order from highest to lowest.
            for (Card card : cards) {
                RankEnum cardRank = card.getRank();
                if (!result.equalsRank(cardRank)) {
                    scores.add(cardRank);
                    if (scores.size() >= numRanksNeeded) {
                        break;
                    }
                }
            }
            finalScore = this.calculateScore(scores);
        }

        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
