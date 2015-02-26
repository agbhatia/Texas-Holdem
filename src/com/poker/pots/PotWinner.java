package com.poker.pots;

import com.poker.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 2/25/15.
 * This class is meant to encapsulate the logic around tracking which players won a given pot, and splitting
 * the winnings between those players.
 */
public class PotWinner {
    // Keep track of the pot that is being looked at, as well as the list of winners.
    private Pot pot;
    private List<Player> winners = new ArrayList<Player>();

    /**
     * Initialize with just the pot
     * @param pot: Pot
     */
    public PotWinner(Pot pot) {
        this.pot = pot;
    }

    /**
     * Initialize with the pot and the winners
     * @param pot: Pot
     * @param winners: List players
     */
    public PotWinner(Pot pot, List<Player> winners) {
        this.pot = pot;
        this.winners = winners;
    }

    /**
     * Add a player as a winner of this pot.
     * @param winner
     */
    public void addWinner(Player winner) {
        winners.add(winner);
    }

    /**
     * When paying out the winners, you want to split the cash evenly amongst each winner. Obviously there can be
     * some remainders though, so we simply just pay each winner 1 unit of the remainder starting at the beginning
     * of the winner list until the remainders are used up.
     */
    public void payWinners() {
        // Just in case need to avoid divide by 0 errors.
        int totalPot = pot.getTotalBet();
        if (totalPot == 0) {
            return;
        }

        // Money should be split evenly, so just divide the bet by the num of winners.
        int moneyPerWinner = pot.getTotalBet()/winners.size();

        // Get the remainder from the division.
        int remainder = pot.getTotalBet() % winners.size();

        for (Player winner : winners) {
            // Give each winner their share.
            winner.incrementCash(moneyPerWinner);
        }

        // Pass the remainders one at a time until they are out.
        for (int i = 0; i < remainder; i++) {
            winners.get(i).incrementCash(1);
        }
    }

    public String toString() {
        if (winners.size() == 1) {
            Player player = winners.get(0);
            return pot + ": " + player + " won " + pot.getTotalBet() + " chips.\nWinning hand: " +
                    player.getHand().getHandEvalResult();
        } else if (winners.size() > 1) {
            Player player = winners.get(0);
            return pot + " was split " + winners.size() + " ways. " + winners + " Winning hand: " +
                player.getHand().getHandEvalResult();
        } else {
            return "No winners";
        }
    }
}
