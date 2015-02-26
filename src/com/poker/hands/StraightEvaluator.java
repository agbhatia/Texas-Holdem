package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class StraightEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.STRAIGHT;

    public StraightEvaluator() {
        super();
    }

    public HandEvalResult evaluate(Card[] cards) {
        int[] ranksByOccurrence = this.ranksByOccurrences(cards);
        boolean hasStraight = false;
        int score = -1;
        int rank = -1;
        int count = 0;

        // To check if we have a straight we use the following logic. We first get the
        // array of ranks by occurrence above. Then we start from the highest value card
        // and decrement down. We keep checking in our array of card ranks to see if the
        // card exists in our set of cards. If it does we increment the number of sequential
        // cards. As soon as we find one that isn't present we reset and start over. If our
        // count of sequential cards hits 5, we have found a straight.
        int maxLength = Card.CardRank.values().length;
        for (int i = maxLength - 1; i >= 0 ; i--) {
            if (ranksByOccurrence[i] == 0) {
                hasStraight = false;
                count = 0;
            } else {
                if (!hasStraight) {
                    hasStraight = true;
                    rank = i;
                }
                count++;
                if (count >= 5) {
                    // Found 5 sequential cards.
                    score = rank;
                    break;
                }
            }
        }
        // Check if the straight is A,2,3,4,5 in which case the 5 is the highest card. This checks if we got
        // all the way down to 5, 4, 3, 2 consecutive. If we did, and we have an ace present, we know we are
        // in a straight.
        if ((count == 4) && (rank == Card.CardRank.FIVE.ordinal()) &&
                (ranksByOccurrence[Card.CardRank.ACE.ordinal()] > 0)) {
            score = rank;
        }
        return new HandEvalResult(score != -1, handType, score);
    }
}
