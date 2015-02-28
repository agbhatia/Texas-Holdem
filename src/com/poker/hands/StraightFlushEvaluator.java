package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;
import com.poker.SuitEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by atul on 2/25/15.
 */
public class StraightFlushEvaluator extends HandEvaluator {

    public StraightFlushEvaluator() {
        super();
    }

    /**
     * This is a fairly complicated algorithm. What we do is first we check to see if we can find a flush. We check
     * for the flush first because we only can have one flush and we want to know exactly what suit to look for.
     * If we go for the straight first we might have duplicate cards and it will be tough to choose the card of
     * the right suit.
     *
     * If we find the flush, then we simply loop through all our cards from highest ranking first and if we find a card
     * that is in our flush suit, we start checking for a straight. we store the last rank in our straight checking,
     * and we continue to keep count of the cards that are in a straight (we can check this by finding a difference
     * in one between their ranks since the cards are ordered). If we hit a count of 5 then we have a straight within
     * the cards that made a flush -- hence a straight flush.
     * @param cards
     * @return
     */
    public HandEvalResult evaluate(List<Card> cards) {
        Map<SuitEnum, Integer> suitByOccurrences = this.suitsByOccurrences(cards);
        SuitEnum flushSuit = null;
        boolean isMatch = false;
        int finalScore = 0;
        HandResult result = HandResult.STRAIGHT_FLUSH;

        // Iterate through our suit indexed map and if we find a suit that has a count >= 5 we have found a match for
        // a flush.
        for (SuitEnum suit : SuitEnum.values()) {
            if (suitByOccurrences.getOrDefault(suit, 0) >= 5) {
                flushSuit = suit;
                isMatch = true;
                break;
            }
        }

        boolean hasStraightFlush = false;
        boolean hasAceInFlush = false;

        if (isMatch) {
            // Now that we found the flush, look for our straight.
            RankEnum lastRank = null;
            RankEnum rank;
            int straightCount = 0;
            RankEnum straightRank = null;

            // Loop through our cards from highest index first.
            for (Card card : cards) {
                // Check to see if the suit of this card matches the suit of the flush we found.
                SuitEnum cardSuit = card.getSuit();
                if (flushSuit.equalsSuit(cardSuit)) {
                    // If so, start checking for a straight. We initially check to see if we have an ace of this suit
                    // to handle dealing with the wheel straight (a, 2, 3, 4, 5).
                    if (card.getRank().equalsRank(RankEnum.ACE)) {
                        hasAceInFlush = true;
                    }

                    // If the last rank is -1 it means we don't have any previous cards in a straight yet.
                    if (lastRank == null) {
                        lastRank = card.getRank();
                    } else {
                        // If last rank has a positive value that means we started a straight. Check this card to see
                        // if it is consecutive with the previous one.
                        rank = card.getRank();
                        if (lastRank.isConsecutive(rank)) {
                            // If it is consecutive, increment the counter. If the count was set to 0, set it to 2 now
                            // since we have the previous card and this one.
                            if (straightCount == 0) {
                                straightCount = 2;
                                straightRank = lastRank;
                            } else {
                                // If we find five in sequence, we have a straight flush.
                                straightCount++;
                                if (straightCount >=5) {
                                    hasStraightFlush = true;
                                    break;
                                }
                            }
                            lastRank = rank;
                        } else {
                            // This card was not 1 off -- reset our variables and keep looking.
                            lastRank = null;
                            straightCount = 0;
                            straightRank = null;
                        }
                    }
                } else if (lastRank != null) {
                    // If this card wasn't part of the suit, check to see if the card was more than 1 away
                    // from the last card in in the straight sequence. If it is more than away reset the
                    // straight. This could be because we have duplicate cards, but some of the duplicates
                    // are not part of the flush.
                    rank = card.getRank();
                    if (!lastRank.isConsecutive(rank) && !lastRank.equalsRank(rank)) {
                        lastRank = null;
                    }
                }
            }

            // for straight flushes, since all the cards are in sequence, we only need the value of the highest
            // card in the straight. that will suffice for the score.
            finalScore = (straightRank == null) ? 0 : straightRank.getValue();
            if (hasStraightFlush && (finalScore == RankEnum.ACE.getValue())) {
                result = HandResult.ROYAL_FLUSH;
            }

            // check to see if we are one away from a straight and the highest rank of the straight is the five.
            // if this is the case then we have a straight flush with the wheel.
            if ((finalScore == RankEnum.FIVE.getValue()) && (straightCount == 4)) {
                Map<RankEnum, Integer> ranksByOccurrence = this.ranksByOccurrences(cards);
                hasStraightFlush = ((ranksByOccurrence.getOrDefault(RankEnum.ACE, 0) > 0) && hasAceInFlush);
            }
        }
        return new HandEvalResult(hasStraightFlush, result, finalScore);
    }
}
