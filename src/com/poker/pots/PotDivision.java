package com.poker.pots;

import com.poker.player.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by atul on 2/25/15.
 * This class is simply a container that gives us the necessary information for dividing a pot into the main pot and its
 * side pot.
 */
public class PotDivision {
    // This is the bet amount that the main pot needs to be set to (it is the amount the players were able to put in).
    private int chipsToDivide;

    // This is the amount to set the side pot to.
    private int limit;
    private boolean shouldLimit;

    // This is the list of players that caused the creation of the side pot, and who should be excluded from the side
    // pot.
    private Set<Player> playersToDivide;

    public PotDivision(int chipsToDivide, Set<Player> playersToDivide) {
        this.playersToDivide = playersToDivide;
        this.chipsToDivide = chipsToDivide;
        limit = 0;
        shouldLimit = false;
    }

    public PotDivision(int chipsToDivide, Player playerToDivide) {
        this.chipsToDivide = chipsToDivide;
        playersToDivide = new HashSet<Player>();
        playersToDivide.add(playerToDivide);
    }

    public void addPlayer(Player player) {
        playersToDivide.add(player);
    }

    public void setLimit(int limit) {
        shouldLimit = true;
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public int getChipsToDivide() {
        return chipsToDivide;
    }

    public boolean shouldLimit() {
        return shouldLimit;
    }

    public Set<Player> getPlayersToDivide() {
        return playersToDivide;
    }
}
