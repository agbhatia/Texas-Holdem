package com.poker.pots;

import com.poker.player.Player;

import java.util.*;

/**
 * Created by atul on 2/25/15.
 * This class keeps track of the pot. The reason this is needed is because in a given hand there can
 * be multiple pots due to players going all in. This class handles keeping track of the last bet in
 * the pot as well, to make sure the min raise amount is respected. It also keeps track of which players
 * have $$ in this pot, so it knows whose hands to check at showdown for the $$ in this pot.
 */
public class Pot {
    private String desc;

    // Important to keep track of the current bet and the bet increment so we know exactly how much a player can raise.
    private int currentBet;
    private int betIncrement;
    private int totalBet;

    // This is used to keep track of the players in the pot.
    private Set<Player> playersInPot;

    public Pot(String desc) {
        this(0, desc);
    }

    public Pot(int bet) {
        this(bet, "");
    }

    public Pot(int bet, String desc) {
        this.desc = desc;
        currentBet = bet;
        playersInPot = new HashSet<Player>();;
        totalBet = bet;
        betIncrement = bet;
    }

    /**
     * Add a player to this pot
     * @param player: Player to add
     */
    public void addPlayer(Player player) {
        playersInPot.add(player);
    }

    /**
     * Retrieve the set of players involved in this pot.
     * @return: Set of players
     */
    public Set<Player> getPlayers() {
        return playersInPot;
    }

    /**
     * This function is a bit complex. It creates a side pot by subtracting out some $$ in this pot and transferring
     * that $$ over to the side pot. The use case is when a player goes all in but has less than the current bet of this
     * pot, we need to create another pot for the extra $$ that the player was not able to cover. We then create a side
     * pot to store that difference, and exclude this player from that side pot (as well as any players no longer active.
     * We also changed the bet amount of this pot to the player's lower all in amount.
     * @param potDivision: Object that store info about the previous min bet and if we need to set a specific amount on the side
     *                     pot (sometimes if multiple players go all in you will need to create multiple side pots.
     * @param sidePotName: String to name the side pot.
     * @return Pot: the side pot.
     */
    public Pot divide(PotDivision potDivision, String sidePotName) {
        // Get the lower bet amount from the potDivision object.
        int betAmount = potDivision.getChipsToDivide();

        // Check if the potDiv object specified a limit for the side pot. If so, use that,
        // otherwise simply take the difference from the lower bet amount and our current bet
        // amount.
        int sidePotAmount = (potDivision.shouldLimit()) ? potDivision.getLimit() : (currentBet - betAmount);

        // Create the side pot with the calculated amount.
        Pot sidePot = new Pot(sidePotAmount, sidePotName);
        Set<Player> potDivisionPlayers = potDivision.getPlayersToDivide();

        // Loop through all the players involved in this pot. For the ones that are listed in the potDiv object,
        // don't add them to the side pot (since they are maxed out on this pot). Also, skip the players that are no
        // longer in the hand (folded, already all in, etc). Only create the side pot for the players that are still
        // active and have bet more already.
        for (Player playerInPot : playersInPot) {
            if (!potDivisionPlayers.contains(playerInPot) && playerInPot.isActive()) {
                sidePot.addPlayer(playerInPot);
                totalBet -= playerInPot.getBet() - betAmount;
            }

        }

        // Switch the current bet to the lower amount.
        currentBet = betAmount;

        // Everyone in this pot should have committed the same amount of $$. So we can refresh simply by doing
        // currentBet * numPlayersInPot.
        refreshTotal();
        sidePot.refreshTotal();
        return sidePot;
    }

    /**
     * Refresh the total $$ in this pot. Each player will be contributing the same amount to each side pot,
     * so it is simple multiplication.
     */
    public void refreshTotal() {
        totalBet = currentBet*playersInPot.size();
    }

    /**
     * Check if a player has $$ in this pot.
     * @param player
     * @return
     */
    public boolean hasPlayer(Player player) {
        return playersInPot.contains(player);
    }

    /**
     * Add a call action from a player into this pot. Call actions should not affect the bet amount.
     * If the player was not already part of this pot, add him to it.
     * @param player: Player that called.
     * @param callAmount: int amount that was called.
     */
    public void addCall(Player player, int callAmount) {
        totalBet += callAmount;

        if (!hasPlayer(player)) {
            playersInPot.add(player);
        }
    }

    /**
     * Add a raise to this pot from a player. Raises will affect the total bet of the pot. It is impt to keep track of
     * the raise increment since that affects the minimum amount that can be raised after this. If the increment is lower
     * than the previous increment already set it means this raise occurred due to an all in that was more than this total
     * bet, but not enough to cover the min raise. In that case we should still update the total bet, but the bet increment
     * should not be touched.
     * @param player: Player that raised
     * @param amount: int amount that was raised.
     */
    public void addRaise(Player player, int amount) {
        totalBet += amount;
        int totalBetByPlayer = player.getBet();
        int raiseIncrement = totalBetByPlayer - currentBet;
        currentBet = totalBetByPlayer;

        // If the player went all in for less than a min raise, we should
        // maintain the previous lastBetIncrement for other raisers. Otherwise
        // if the amount the player raised is more than the previous increment
        // overwrite the previous increment.
        if (raiseIncrement >= betIncrement) {
            betIncrement = raiseIncrement;
        }

        if (!hasPlayer(player)) {
            playersInPot.add(player);
        }
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public int getBetIncrement() {
        return betIncrement;
    }

    public int getTotalBet() {
        return totalBet;
    }

    /**
     * Reset the bets in this pot (will be called after a round of betting). Note that this will not reset the
     * totalBet since we need to keep track of that value across the lifetime of a hand.
     */
    public void resetBets() {
        currentBet = 0;
        betIncrement = 0;
    }

    public String toString() {
        return desc;
    }

    public void setCurrentBet(int amount) {
        currentBet = amount;
    }

    public void setBetIncrement(int amount) {
        betIncrement = amount;
    }
}
