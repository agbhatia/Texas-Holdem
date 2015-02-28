package com.poker.hands;

import com.poker.Card;
import com.poker.RankEnum;
import com.poker.SuitEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by atul on 2/25/15.
 * This is an abstract class meant to evaluate different types of hands.
 * We create an instance of every type of evaluator and each evaluator
 * knows how to evaluate that specific hand.
 */
public abstract class HandEvaluator {

    abstract public HandEvalResult evaluate(List<Card> cards);

    private static final int[] SCORING_WEIGHT = {13*13*13*13, 13*13*13, 13*13, 13, 1};

    /**
     * Return an array of the cards where the index is the card rank as an integer, and the value
     * is a count of the appearances of that card in the card set. This is super useful in easily
     * identifying hand sequences. Also because the ranks are from low to high, we know that the
     * highest ranked cards are at the end. So if we traverse the list backwards we can easily find
     * the first card that is repeated two times.
     * @param cards: List of cards to analyze
     * @return: Map(RankEnum, Integer) rankEnum tells us info about the card, and integer is the num
     * of occurrences that rank shows up in our list of cards.
     */
    protected Map<RankEnum, Integer> ranksByOccurrences(List<Card> cards) {
        Map<RankEnum, Integer> returnVal = new HashMap<>(RankEnum.numRanks());
        for (Card card : cards) {
            // Check to see if this rank is already in our map. If so, increment 1 to the count, else set
            // the count to 1.
            returnVal.compute(card.getRank(), (tokenKey, oldValue) -> oldValue == null ? 1 : oldValue + 1);
        }
        return returnVal;
    }

    /**
     * Return an Map of the cards where the key is the card suit, and the value is a count of the appearances
     * of that suit in the card set. The use case here is for identifying flushes.
     * @param cards: List of cards
     * @return Map(SuitEnum, Integer)
     */
    protected Map<SuitEnum, Integer> suitsByOccurrences(List<Card> cards) {
        Map<SuitEnum, Integer> returnVal = new HashMap<>(SuitEnum.numSuits());
        for (Card card : cards) {
            returnVal.compute(card.getSuit(), (tokenKey, oldValue) -> oldValue == null ? 1 : oldValue + 1);
        }
        return returnVal;
    }

    /**
     * Find the highest card rank that shows up as least numDuplicates amount of times in our card set.
     * This function uses our map of ranks which is keyed by rank and the value is the number of
     * of appearances. We created this function for the usages of calculating three of a kind / four of a
     * kind.
     * @param cards
     * @param numDuplicates
     * @return
     */
    protected RankEnum findHighestDuplicate(List<Card> cards, int numDuplicates) {
        Map<RankEnum, Integer> ranksByOccurrences = ranksByOccurrences(cards);
        // We loop through our rank enums which we know is in order from highest to lowest. This way we can get
        // the highest first.
        for (RankEnum rank : RankEnum.values()) {
            if (ranksByOccurrences.getOrDefault(rank, 0) == numDuplicates) {
                return rank;
            }
        }
        return null;
    }

    /**
     * For each set of cards, we have to figure out the highest card type it belongs to. But we also have to solve
     * for cases where other hands have the same type of hand but of a different value. For example two hands could
     * both be straights, but one straight is higher than the other. Both hands could be flushes with one flush higher.
     * This function calculates a score by weighting each element in the score array. It is impt to note that if the
     * highest card has a higher value it should always win, regardless of what the other cards are. That is why
     * we use a weighting system with powers of 13 (since there are 13 cards of each suit) so that even if another hand
     * has a higher 2nd/3rd value if the 1st value is lower it will return a lower score no matter what.
     * @param scores: List of scores that need to be weighted.
     * @return: int score
     */
    protected int calculateScore(List<RankEnum> scores) {
        int result = 0;
        int i = -1;
        // traverse through the scores and weigh them appropriately.
        for (RankEnum rank : scores) {
            i++;
            result += rank.getValue()*SCORING_WEIGHT[i];
        }
        return result;
    }

    /**
     * This function finds the top X number of pairs. Used for finding two pair / one pair hands. Also used
     * for finding the pair in the full house evaluator.
     * @param cards
     * @param numPairs
     * @return: List of pairs up to the num pairs expected.
     */
    protected List<RankEnum> findTopNPairs(List<Card> cards, int numPairs) {
        List<RankEnum> pairs = new ArrayList<>();

        // First we map our cards such that rank id is keyed, and value is # appearances.
        // Then we start searching traverse through our list of ranks where the highest
        // rank is first and find up to n values where the num appearances = 2.
        Map<RankEnum, Integer> ranksByOccurrences = ranksByOccurrences(cards);
        for (RankEnum rank : RankEnum.values()) {
            if (pairs.size() == numPairs) break;

            if (ranksByOccurrences.getOrDefault(rank, 0) == 2) {
                pairs.add(rank);
            }
        }

        return pairs;
    }
}

