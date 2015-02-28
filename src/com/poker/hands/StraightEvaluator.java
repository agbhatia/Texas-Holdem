package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by atul on 2/25/15.
 */
public class StraightEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.STRAIGHT;

    public StraightEvaluator() {
        super();
    }

    public HandEvalResult evaluate(List<Card> cards) {
        Map<RankEnum, Integer> ranksByOccurrence = this.ranksByOccurrences(cards);
        boolean hasStraight = false;
        int score = -1;
        RankEnum rank = null;
        int count = 0;

        // To check if we have a straight we use the following logic. We first get the
        // map of ranks by occurrence above. Then we iterate through our rank of cards
        // descending. We keep checking in our array of card ranks to see if the
        // card exists in our set of cards. If it does we increment the number of sequential
        // cards. As soon as we find one that isn't present we reset and start over. If our
        // count of sequential cards hits 5, we have found a straight.

        for (RankEnum rankEnum : RankEnum.values()) {
            if (ranksByOccurrence.getOrDefault(rankEnum, 0) == 0) {
                hasStraight = false;
                count = 0;
            } else {
                if (!hasStraight) {
                    hasStraight = true;
                    rank = rankEnum;
                    count = 1;
                } else {
                    count++;
                }

                if (count >= 5) {
                    score = rank.getValue();
                }
            }
        }

        // Check if the straight is A,2,3,4,5 in which case the 5 is the highest card. This checks if we got
        // all the way down to 5, 4, 3, 2 consecutive. If we did, and we have an ace present, we know we are
        // in a straight.
        if ((count == 4) && (rank.equalsRank(RankEnum.FIVE)) &&
                (ranksByOccurrence.getOrDefault(RankEnum.ACE, 0) > 0)) {
            score = rank.getValue();
        }
        return new HandEvalResult(score != -1, handType, score);
    }
}
