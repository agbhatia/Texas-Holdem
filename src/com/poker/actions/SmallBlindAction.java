package com.poker.actions;

/**
 * Created by atul on 2/24/15.
 */
public class SmallBlindAction extends Action {

    public SmallBlindAction(int incrementAmount) {
        super(ActionEnum.SMALL_BLIND, incrementAmount, "Small Blind");
    }

    @Override
    public String description() {
        return String.format("posts small blind of %s chips", this.incrementAmount);
    }
}
