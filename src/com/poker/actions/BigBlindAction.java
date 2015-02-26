package com.poker.actions;

/**
 * Created by atul on 2/24/15.
 */
public class BigBlindAction extends Action {

    public BigBlindAction(int incrementAmount) {
        super(ActionEnum.BIG_BLIND, incrementAmount, "Big Blind");
    }

    @Override
    public String description() {
        return String.format("posts big blind of %s chips", incrementAmount);
    }
}
