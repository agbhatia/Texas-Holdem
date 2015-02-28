package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/25/15.
 */
public class OnePairEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.ONE_PAIR;
    private static final int numRanksNeeded = 4;



    public OnePairEvaluator() {
        super();
    }

    /**
     * Checks if we have one pair in this set of cards
     * @param cards
     * @return
     */
    public HandEvalResult evaluate(List<Card> cards) {
        // Find the top pair in our set of cards.
        List<RankEnum> pairs = this.findTopNPairs(cards, 1);
        int finalScore = 0;
        List<RankEnum> scores = new ArrayList<>();
        boolean isMatch = pairs.size() >= 1;

        // If we did find one pair, then we simply need to loop through our cards starting from the highest rank
        // (which is at the beginning of the array) and find 3 cards that do not match the pair since we need
        // three kickers. Then, calculate the final score of the pair + kickers.
        if (isMatch) {
            RankEnum pairRank = pairs.get(0);
            scores.add(pairRank);
            for (Card card : cards) {
                // We can use this as a kicker if it is not equal to the ranks of any of our pairs.
                RankEnum cardRank = card.getRank();
                if (!pairRank.equalsRank(cardRank)) {
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
