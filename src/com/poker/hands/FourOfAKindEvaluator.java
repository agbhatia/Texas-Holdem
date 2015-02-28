package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/25/15.
 */
public class FourOfAKindEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.FOUR_OF_A_KIND;

    // We need to track two card ranks. 1 is the card that repeats 4 times, and the other is the kicker.
    private static final int numRanksNeeded = 2;

    public FourOfAKindEvaluator() {
        super();
    }

    public HandEvalResult evaluate(List<Card> cards) {
        // To calculate the score we get the value that is repeated 4 times, and the highest kicker. Then we
        // weigh each accordingly (the value repeated 4 times is obviously weighted the most).
        List<RankEnum> scores = new ArrayList<>();

        // First find the highest number that repeats exactly 4 times.
        RankEnum result = this.findHighestDuplicate(cards, 4);
        int finalScore = 0;
        boolean isMatch = result != null;

        if (isMatch) {
            scores.add(result);
            // Traverse cards which is already in order from highest to lowest.
            for (Card card : cards) {
                RankEnum otherRank = card.getRank();
                if (!result.equalsRank(otherRank)) {
                    scores.add(otherRank);
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
