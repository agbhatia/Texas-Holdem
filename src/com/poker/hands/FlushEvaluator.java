package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class FlushEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.FLUSH;

    public FlushEvaluator() {
        super();
    }

    /**
     * To check if we have a flush, we first index by suit to see if we have 5 cards or more of the same suit.
     * Note that you cannot have two different flushes since there are only 7 cards. If we found a suit, then
     * we calculate the score of the 5 highest of that suit (we iterate the cards in order since it is sorted
     * by rank).
     * @param cards
     * @return
     */
    public HandEvalResult evaluate(Card[] cards) {
        // Get cards indexed by suit
        int[] suitByOccurrences = this.suitsByOccurrences(cards);
        int maxLength = Card.CardSuit.values().length;
        int suitOrdinal = -1;
        int[] scores = new int[5];
        boolean isMatch = false;
        int index = 0;
        int finalScore = 0;

        // Iterate through our suit indexed array and if we find a suit that has a count >= 5 we have found a match.
        for (int i = maxLength - 1; i >= 0 ; i--) {
            if (suitByOccurrences[i] >= 5) {
                suitOrdinal = i;
                isMatch = true;
                break;
            }
        }

        // If we found a suit, find the 5 highest cards that match it.
        if (isMatch) {
            for (Card card : cards) {
                int suitRank = card.suitToInt();
                if (suitRank == suitOrdinal) {
                    scores[index] = card.rankToInt();
                    index++;
                    if (index >= scores.length) {
                        break;
                    }
                }
            }
            // Calculate our final score.
            finalScore = this.calculateScore(scores);
        }
        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
