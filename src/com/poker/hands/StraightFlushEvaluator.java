package com.poker.hands;

import com.poker.Card;

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
    public HandEvalResult evaluate(Card[] cards) {
        int[] suitByOccurrences = this.suitsByOccurrences(cards);
        int maxLength = Card.CardSuit.values().length;
        int suitOrdinal = -1;
        boolean isMatch = false;
        int finalScore = 0;
        HandResult result = HandResult.STRAIGHT_FLUSH;

        // Iterate through our suit indexed array and if we find a suit that has a count >= 5 we have found a match for
        // a flush.
        for (int i = maxLength - 1; i >= 0 ; i--) {
            if (suitByOccurrences[i] >= 5) {
                suitOrdinal = i;
                isMatch = true;
                break;
            }
        }

        boolean hasStraightFlush = false;
        boolean hasAceInFlush = false;

        if (isMatch) {
            // Now that we found the flush, look for our straight.
            int lastRank = -1;
            int rank = -1;
            int straightCount = 0;
            int straightRank = -1;

            // Loop through our cards from highest index first.
            for (Card card : cards) {
                // Check to see if the suit of this card matches the suit of the flush we found.
                int suitRank = card.suitToInt();
                if (suitRank == suitOrdinal) {
                    // If so, start checking for a straight. We initially check to see if we have an ace of this suit
                    // to handle dealing with the wheel straight (a, 2, 3, 4, 5).
                    if (card.getRank() == Card.CardRank.ACE) {
                        hasAceInFlush = true;
                    }

                    // If the last rank is -1 it means we don't have any previous cards in a straight yet.
                    if (lastRank == -1) {
                        lastRank = card.rankToInt();
                    } else {
                        // If last rank has a positive value that means we started a straight. Check this card to see
                        // if it is consecutive with the previous one.
                        rank = card.rankToInt();
                        int diff = lastRank - rank;
                        if (diff == 1) {
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
                            lastRank = -1;
                            rank = -1;
                            straightCount = 0;
                            straightRank = -1;
                        }
                    }
                } else if (lastRank != -1) {
                    rank = card.rankToInt();
                    int diff = lastRank - rank;
                    if (diff > 1) {
                        lastRank = -1;
                    }
                }
            }

            // for straight flushes, since all the cards are in sequence, we only need the value of the highest
            // card in the straight. that will suffice for the score.
            finalScore = straightRank;
            if (hasStraightFlush && (finalScore == Card.CardRank.ACE.ordinal())) {
                result = HandResult.ROYAL_FLUSH;
            }

            // check to see if we are one away from a straight and the highest rank of the straight is the five.
            // if this is the case then we have a straight flush with the wheel.
            if ((straightRank == Card.CardRank.FIVE.ordinal()) && (straightCount == 4)) {
                int[] ranksByOccurrence = this.ranksByOccurrences(cards);
                hasStraightFlush = ((ranksByOccurrence[Card.CardRank.ACE.ordinal()] > 0) && hasAceInFlush);

            }
        }
        return new HandEvalResult(hasStraightFlush, result, finalScore);
    }
}
