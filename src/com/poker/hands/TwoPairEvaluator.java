package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class TwoPairEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.TWO_PAIR;

    public TwoPairEvaluator() {
        super();
    }

    /**
     * Checks if we have a two pair in this set of cards
     * @param cards
     * @return
     */
    public HandEvalResult evaluate(Card[] cards) {
        // Find the top 2 pairs in our set of cards.
        Pair pairs = this.findTopNPairs(cards, 2);
        int finalScore = 0;
        int[] scores = new int[3];
        boolean isMatch = pairs.foundAllPairs;

        // If we did find 2 pairs, then we simply need to loop through our cards starting from the highest rank
        // (which is at the beginning of the array) and find a card that does not match one of the pairs since
        // we need one kicker. Then, calculate the final score of the two pair.
        if (isMatch) {
            int[] pairRanks = pairs.pairs;
            scores[0] = pairRanks[0];
            scores[1] = pairRanks[1];
            for (Card card : cards) {
                int cardRank = card.rankToInt();

                // We can use this as a kicker if it is not equal to the ranks of any of our pairs.
                if ((cardRank != scores[0]) && (cardRank != scores[1])) {
                    scores[2] = cardRank;
                    break;
                }
            }
            finalScore = this.calculateScore(scores);

        }
        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
