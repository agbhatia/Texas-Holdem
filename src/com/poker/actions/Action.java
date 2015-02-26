package com.poker.actions;

/**
 * Created by atul on 2/24/15.
 * This class represents an action that a player can perform. It encapsulates the action type and the amount
 * of $$ associated with the action. Some actions do not have an amount associated in which case we simply use
 * the value 0 for those.
 */
abstract public class Action {
    private final ActionEnum actionEnum;
    protected final int incrementAmount;
    protected final int totalAmount;
    protected final String desc;

    public Action(ActionEnum actionEnum, String desc) {
        this.actionEnum = actionEnum;
        incrementAmount = 0;
        totalAmount = 0;
        this.desc = desc;
    }

    public Action(ActionEnum actionEnum, int incrementAmount, String desc) {
        this.incrementAmount = incrementAmount;
        this.totalAmount = 0;
        this.actionEnum = actionEnum;
        this.desc = desc;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public int getIncrementAmount() {
        return incrementAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String toString() {
        return desc;
    }

    public abstract String description();
}


