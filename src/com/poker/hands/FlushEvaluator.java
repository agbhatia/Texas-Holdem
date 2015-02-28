package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;
import com.poker.SuitEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by atul on 2/25/15.
 */
public class FlushEvaluator extends HandEvaluator {
    private static final HandResult handType = HandResult.FLUSH;

    private static final int numRanks = 2;

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
    public HandEvalResult evaluate(List<Card> cards) {
        // Get cards indexed by suit
        Map<SuitEnum, Integer> suitByOccurrences = this.suitsByOccurrences(cards);
        SuitEnum flushSuit = null;
        List<RankEnum> scores = new ArrayList<>();
        boolean isMatch = false;
        int finalScore = 0;

        // Iterate through our suit indexed map and if we find a suit that has a count >= 5 we have found a match.
        for (SuitEnum suit : SuitEnum.values()) {
            if (suitByOccurrences.getOrDefault(suit, 0) >= 5) {
                flushSuit = suit;
                isMatch = true;
                break;
            }
        }

        // If we found a suit, find the 5 highest cards that match it.
        if (isMatch && flushSuit != null) {
            for (Card card : cards) {
                if (flushSuit.equalsSuit(card.getSuit())) {
                    scores.add(card.getRank());
                    if (scores.size() >= numRanks) {
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
