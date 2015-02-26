package com.poker.player;

import java.util.Comparator;

/**
 * Created by atul on 2/26/15.
 */
public class PlayerHandComparator implements Comparator<Player> {

    @Override
    public int compare(Player player1, Player player2) {
        return player1.getHand().getHandEvalResult().compareTo(player2.getHand().getHandEvalResult());
    }
}
