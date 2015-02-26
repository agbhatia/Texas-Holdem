package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 * This is an abstract class meant to evaluate different types of hands.
 * We create an instance of every type of evaluator and each evaluator
 * knows how to evaluate that specific hand.
 */
public abstract class HandEvaluator {

    abstract public HandEvalResult evaluate(Card[] cards);

    private static final int[] SCORING_WEIGHT = {13*13*13*13, 13*13*13, 13*13, 13, 1};

    /**
     * Return an array of the cards where the index is the card rank as an integer, and the value
     * is a count of the appearances of that card in the card set. This is super useful in easily
     * identifying hand sequences. Also because the ranks are from low to high, we know that the
     * highest ranked cards are at the end. So if we traverse the list backwards we can easily find
     * the first card that is repeated two times.
     * @param cards: Array of cards to analyze
     * @return: Array of cards indexed by rank and valued by appearances.
     */
    protected int[] ranksByOccurrences(Card[] cards) {
        int[] returnVal = new int[Card.CardRank.values().length];
        for (Card card : cards) {
            returnVal[card.rankToInt()]++;
        }
        return returnVal;
    }

    /**
     * Return an array of the cards where the index is the card suit as an integer, and the value is
     * a count of the appearances of that suit in the card set. The use case here is for identifying flushes.
     * @param cards
     * @return
     */
    protected int[] suitsByOccurrences(Card[] cards) {
        int[] returnVal = new int[Card.CardSuit.values().length];
        for (Card card : cards) {
            returnVal[card.suitToInt()]++;
        }
        return returnVal;
    }

    /**
     * Find the highest card rank that shows up as least numDuplicates amount of times in our card set.
     * This function leverages the ability of indexing the cards by rank int where the value is the num
     * of appearances. We created this function for the usages of calculating three of a kind / four of a
     * kind.
     * @param cards
     * @param numDuplicates
     * @return
     */
    protected int findHighestDuplicate(Card[] cards, int numDuplicates) {
        int[] ranksByOccurrences = ranksByOccurrences(cards);
        int numRanks = Card.CardRank.values().length;
        for (int i = numRanks - 1; i > 0; i--) {
            if (ranksByOccurrences[i] == numDuplicates) {
                return i;
            }
        }
        return -1;

    }

    /**
     * For each set of cards, we have to figure out the highest card type it belongs to. But we also have to solve
     * for cases where other hands have the same type of hand but of a different value. For example two hands could
     * both be straights, but one straight is higher than the other. Both hands could be flushes with one flush higher.
     * This function calculates a score by weighting each element in the score array. It is impt to note that if the
     * highest card has a higher value it should always win, regardless of what the other cards are. That is why
     * we use a weighting system with powers of 13 (since there are 13 cards of each suit) so that even if another hand
     * has a higher 2nd/3rd value if the 1st value is lower it will return a lower score no matter what.
     * @param scores: int[] of scores that need to be weighted.
     * @return: int score
     */
    protected int calculateScore(int[] scores) {
        int result = 0;
        // traverse through the scores and weight them appropriately.
        for (int i = 0; i < scores.length; i++) {
            result += scores[i]*SCORING_WEIGHT[i];
        }
        return result;
    }

    /**
     * This function finds the top X number of pairs. Used for finding two pair / one pair hands. Also used
     * for finding the pair in the full house evaluator.
     * @param cards
     * @param numPairs
     * @return: Pair object that contains the list of pairs and if we were able to find all the pairs we were
     * looking for.
     */
    protected Pair findTopNPairs(Card[] cards, int numPairs) {
        int[] pairs = new int[numPairs];
        int foundPairs = 0;

        // First we index our cards such that rank id is index, and value is # appearances.
        // Then we start searching backwards starting at the highest rank and find up to n
        // values where the num appearances = 2.
        int[] ranksByOccurrences = ranksByOccurrences(cards);
        int numRanks = Card.CardRank.values().length;
        for (int i = numRanks - 1; i > 0; i--) {
            if (foundPairs == numPairs) break;

            if (ranksByOccurrences[i] == 2) {
                pairs[foundPairs] = i;
                foundPairs++;
            }
        }

        Pair returnVal = new Pair();
        returnVal.pairs = pairs;
        returnVal.foundAllPairs = foundPairs == numPairs;
        return returnVal;
    }

    /**
     * Container class for information regarding pairs and num pairs found.
     */
    protected class Pair {
        int[] pairs;
        boolean foundAllPairs;
    }
}

