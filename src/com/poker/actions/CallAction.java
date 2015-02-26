package com.poker.actions;

/**
 * Created by atul on 2/24/15.
 */
public class CallAction extends Action {

    public CallAction(int incrementAmount) {
        super(ActionEnum.CALL, incrementAmount, "Call");
    }

    @Override
    public String description() {
        return String.format("calls %s chips", incrementAmount);
    }
}
