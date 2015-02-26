package com.poker.pots;

import com.poker.hands.Hand;
import com.poker.hands.HandEvalResult;
import com.poker.player.Player;
import com.poker.player.PlayerHandComparator;

import java.util.*;

/**
 * Created by atul on 2/25/15.
 * This class is used to track all the pots that are part of a given hand. Because there can be multiple pots due to
 * players going all in, we need a place to store the list of all our pots. This is impt with tracking who is involved
 * in which pot, so that when we calculate the winners of each pot, we know how much $$ to pay them.
 */
public class PotManager {

    // This tracks all of the pots in a hand.
    private List<Pot> pots;

    private final int bigBlind;
    private final int smallBlind;
    private Pot currentPot;

    public PotManager(int smallBlind, int bigBlind) {
        // Create the initial pot. This will be called the 'Main Pot'
        currentPot = new Pot("Main Pot");
        pots = new ArrayList<Pot>();
        pots.add(currentPot);
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;
    }

    /**
     * Post a small blind from a player into the pot.
     * @param player: Player that posted small blind.
     * @param amount: Amount of small blind.
     */
    public void postSmallBlind(Player player, int amount) {
        currentPot.addRaise(player, amount);

        // If the small blind posted is lower than the actual small blind it means this player only has this many
        // chips left. We still want to maintain the lowest bet as the regular small blind amount though.
        if (amount < smallBlind) {
            currentPot.setCurrentBet(smallBlind);
            currentPot.setBetIncrement(smallBlind);
        }
    }

    /**
     * Post the big blind from a player into the pot.
     * @param player: Player that posted big blind.
     * @param amount: amount of big blind
     */
    public void postBigBlind(Player player, int amount) {
        currentPot.addRaise(player, amount);
        currentPot.setBetIncrement(bigBlind);

        // If the amount posted is smaller than the game defined big blind, that means this player has used all his
        // chips. We want to make sure the actual big blind is used as the current bet.
        if (amount < bigBlind) {
            currentPot.setCurrentBet(bigBlind);
        }
    }

    /**
     * Add a call into the pot from a player.
     * @param player: Player that called.
     * @param callAmount: int amount that was called.
     */
    public void addCall(Player player, int callAmount) {
        currentPot.addCall(player, callAmount);
    }

    /**
     * Add a raise into the pot from a player.
     * @param player: Player that raised.
     * @param raiseAmount: int amount that was raised.
     */
    public void addRaise(Player player, int raiseAmount) {
        currentPot.addRaise(player, raiseAmount);
    }

    /**
     * After a round of betting we need to clean up the state. We need to look at all the bets and see if we need to
     * create any side pots. This is done by checking the currentBet of the pot and checking that against each player
     * involved in the pot to see if that player's bet is below the currentBet val. If it is, that means that the
     * player didn't have enough chips to match the bet and is all in. Create side pots accordingly.
     */
    public void closePotBetting() {
        Set<Player> playersInPot = currentPot.getPlayers();
        int betAmount = currentPot.getCurrentBet();
        List<Player> playersBelowBet = new ArrayList<Player>();
        for (Player player: playersInPot) {
            // Traverse through each player in the pot and find those that are below the bet amount. Ignore the
            // players that are either inactive (folded) or were all in from a previous round.
            if ((player.getBet() < betAmount) && (player.isActive())) {
                playersBelowBet.add(player);
            }
        }

        // Once we get all the players sort them in ascending order by the value of their bets. This is so in case
        // we need to create multiple side pots, we will start from the smallest one and work forward.
        Collections.sort(playersBelowBet);

        // Calculate the potDivision objects that tell us exactly how many side pots will need to be created and
        // what the bet amount should be set to for each of those side pots, and which players need to be excluded
        // from those side pots.
        List<PotDivision> potDivisions = calculatePotDivisions(playersBelowBet, betAmount);
        for (PotDivision potDivision : potDivisions) {
            int numPots = pots.size();
            String nameForPot = "Side Pot " + (numPots);

            // Get the side pot and add it to our list of pots.
            Pot sidePot = currentPot.divide(potDivision, nameForPot);
            pots.add(sidePot);

            // Set our current pot to the last side pot since that is the pot that the active players will be
            // betting into now.
            currentPot = sidePot;
        }
        // This function is called after a betting round, so clean up the currentBets in each side pot so a new
        // betting round can start.
        cleanupPots();
    }

    /**
     * Cleanup pots after a betting round is complete.
     */
    private void cleanupPots() {
        // Clean out the current bets in each pot.
        for (Pot pot : pots) {
            pot.resetBets();
            pot.setBetIncrement(bigBlind);
        }
    }

    /**
     * Calculate the potDivisions that tell us how many side pots need to be created and what the current bet amount
     * of the side pots should be set to.
     * @param playersToDivide: List of players that are all in for less than the bet amount.
     * @param fullBet: int amount of the betAmount of the current pot.
     * @return: List of potDivisions that tell us how to create the side pots.
     */
    private List<PotDivision> calculatePotDivisions(List<Player> playersToDivide, int fullBet) {
        int bet = -1;
        int numChips = 0;
        List<PotDivision> divisionList = new ArrayList<PotDivision>();
        PotDivision currentDivision = null;

        // Traverse through each player below the bet amount.
        for (Player player : playersToDivide) {
            int playerChips = player.getBet();
            // If this player has bet more than the current bet then create a new pot division for him.
            if (playerChips > bet) {
                // We calculate the remaining chips that should be used incrementally. If one person went all in
                // for 300, and the player after him went all in for 500, 300 of the 2nd players cash will go
                // into the first side pot, and the remaining (500-300 = 200) will go into the next side pot.
                int remainingChips = playerChips - numChips;
                PotDivision divisionToAdd = new PotDivision(remainingChips, player);
                divisionList.add(divisionToAdd);

                // Set the limit for the side pot on the previous division to our remaining chips.
                if (currentDivision != null) {
                    currentDivision.setLimit(remainingChips);
                }
                currentDivision = divisionToAdd;
                numChips += remainingChips;
                bet = playerChips;
            } else if ((playerChips == bet) && (currentDivision != null)) {
                // If the player's bet equals the previous bet, then that means we want to add this player to
                // the existing division because both players went all in for the same amount and they both
                // need to be included from the side pot. (Imagine two players going all in for 500 on a bet of 1000).
                currentDivision.addPlayer(player);
            }
        }

        // At the end we check to see the difference between the actual bet of this pot before and the largest bet
        // from one of our all in players that was below the limit. The final side pot created needs its bet to be
        // set to this amount.
        if (currentDivision != null) {
            currentDivision.setLimit(fullBet-numChips);
        }
        return divisionList;
    }

    /**
     * Get the total value of the $$ put in by adding up the total values of each pot
     * @return
     */
    public int getTotalPot() {
        int potValue = 0;
        for (Pot pot : pots) {
            potValue += pot.getTotalBet();
        }
        return potValue;
    }

    /**
     * Retrieve the last bet of our current pot.
     * @return int bet
     */
    public int getLastBet() {
        return currentPot.getCurrentBet();
    }

    public int getBetIncrement() {
        return currentPot.getBetIncrement();
    }

    public int getMinimumBetAmount() {

        return getLastBet() + getBetIncrement();
    }

    public String toString() {
        String str = "Total Pot: " + getTotalPot();

        if (pots.size() > 1) {
            for (Pot pot : pots) {
                str += "\n" + pot + ": " + pot.getTotalBet() + "\n";
            }
        }
        return str;

    }

    /**
     * This function calculates the winners of each pot, and returns a list of potWinners indicating the winners
     * of each pot. It takes in the last of activePlayers still in the hand. It then goes through each pot to see
     * which of the active players were involved with that pot. Then for the active players that were involved it sorts
     * them by their hand strength, and determines the winners of that pot.
     * @param activePlayers
     * @return
     */
    public List<PotWinner> calculateWinners(List<Player> activePlayers) {
        List<PotWinner> potWinners = new ArrayList<PotWinner>();
        for (Pot pot : pots) {
            // Find the active players in each pot.
            List<Player> potPlayers = new ArrayList<Player>();
            for (Player player : activePlayers) {
                if (pot.hasPlayer(player)) {
                    potPlayers.add(player);
                }
            }

            // Sort the players using the hand comparator.
            Collections.sort(potPlayers, new PlayerHandComparator());
            PotWinner potWinner = new PotWinner(pot);
            HandEvalResult lastHandEvalResult = null;

            // Track the list of winners by keeping in mind some players might have equal hands.
            for (Player player : potPlayers) {
                HandEvalResult thisHandEval = player.getHand().getHandEvalResult();
                if (lastHandEvalResult == null) {
                    potWinner.addWinner(player);
                    lastHandEvalResult = thisHandEval;
                } else if (lastHandEvalResult.compareTo(thisHandEval) == 0) {
                    potWinner.addWinner(player);
                } else {
                    break;
                }
            }

            potWinners.add(potWinner);

        }
        return potWinners;
    }

    public List<Pot> getPots() {
        return pots;
    }
}
