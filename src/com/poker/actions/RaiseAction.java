package com.poker.actions;

/**
 * Created by atul on 2/24/15.
 */
public class RaiseAction extends Action {

    public RaiseAction(int incrementAmount) {
        super(ActionEnum.RAISE, incrementAmount, "Raise");
    }

    @Override
    public String description() {
        return String.format("raises %s more chips", incrementAmount);
    }
}
