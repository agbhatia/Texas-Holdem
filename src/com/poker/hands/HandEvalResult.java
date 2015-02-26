package com.poker.hands;

import com.poker.Card;

/**
 * Created by atul on 2/25/15.
 * This class encapsulates the result of the hand evaluator. We keep a boolean to indicate if the hand actually matched,
 * the enum result to indicate what kind of hand this is, and the score so that we can differentiate between hands
 * of the same enum.
 *
 */
public class HandEvalResult implements Comparable<HandEvalResult> {
    private boolean matches;
    private HandResult result;
    private int score;

    public HandEvalResult(boolean isMatch, HandResult result, int score) {
        this.matches = isMatch;
        this.result = result;
        this.score = score;
    }

    public boolean isMatch() {
        return matches;
    }

    public HandResult result() {
        return result;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(HandEvalResult otherResult) {
        int myResultOrdinal = this.result.ordinal();
        int otherResultOrdinal = otherResult.result.ordinal();
        if (myResultOrdinal > otherResultOrdinal) {
            return -1;
        } else if (myResultOrdinal < otherResultOrdinal) {
            return 1;
        } else {
            // If equal, check the score
            int myScore = this.getScore();
            int otherScore = otherResult.getScore();
            return myScore > otherScore ? -1 : myScore < otherScore ? +1 : 0;
        }
    }

    public String toString() {
        if (isMatch()) {
            return result.toString();
        } else {
            return super.toString();
        }
    }

}
