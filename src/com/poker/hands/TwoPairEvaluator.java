package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;

import java.util.ArrayList;
import java.util.List;

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
    public HandEvalResult evaluate(List<Card> cards) {
        // Find the top 2 pairs in our set of cards.
        List<RankEnum> pairs = this.findTopNPairs(cards, 2);
        List<RankEnum> scores = new ArrayList<>();

        int finalScore = 0;
        boolean isMatch = pairs.size() >= 2;

        // If we did find 2 pairs, then we simply need to loop through our cards starting from the highest rank
        // (which is at the beginning of the array) and find a card that does not match one of the pairs since
        // we need one kicker. Then, calculate the final score of the two pair.
        if (isMatch) {
            RankEnum pair1 = pairs.get(0);
            RankEnum pair2 = pairs.get(1);
            scores.add(pair1);
            scores.add(pair2);

            for (Card card : cards) {
                RankEnum cardRank = card.getRank();

                // We can use this as a kicker if it is not equal to the ranks of any of our pairs.
                if ((!pair1.equalsRank(cardRank)) && (!pair2.equalsRank(cardRank))) {
                    scores.add(cardRank);
                    break;
                }
            }
            finalScore = this.calculateScore(scores);

        }
        return new HandEvalResult(isMatch, handType, finalScore);
    }
}
