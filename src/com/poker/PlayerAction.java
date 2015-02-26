package com.poker;

import com.poker.actions.Action;
import com.poker.player.Player;

/**
 * Created by atul on 2/24/15.
 */
public class PlayerAction {
    private Player player;
    private Action action;

    public PlayerAction(Player player, Action action) {
        this.player = player;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public Player getPlayer() {
        return player;
    }

    public String toString() {
        return String.format("%s %s", player, action.description());
    }
}
