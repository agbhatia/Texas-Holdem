package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 */
public class OnePairEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.ONE_PAIR;

    public OnePairEvaluator() {
        super();
    }

    /**
     * Checks if we have one pair in this set of cards
     * @param cards
     * @return
     */
    public HandEvalResult evaluate(Card[] cards) {
        // Find the top pair in our set of cards.
        Pair pairs = this.findTopNPairs(cards, 1);
        int finalScore = 0;
        int[] scores = new int[4];
        boolean isMatch = pairs.foundAllPairs;

        // If we did find one pair, then we simply need to loop through our cards starting from the highest rank
        // (which is at the beginning of the array) and find 3 cards that do not match the pair since we need
        // three kickers. Then, calculate the final score of the pair + kickers.
        if (isMatch) {
            int[] pairRanks = pairs.pairs;
            scores[0] = pairRanks[0];
            int i = 1;
            for (Card card : cards) {
                int cardRank = card.rankToInt();

                // We can use this as a kicker if it is not equal to the ranks of any of our pairs.
                if (cardRank != scores[0]) {
                    scores[i] = cardRank;
                    i++;
                    if (i > 3) {
                        break;
                    }
                }
            }
            finalScore = this.calculateScore(scores);

        }
        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
