package com.poker.actions;

/**
 * Created by atul on 2/24/15.
 */
public class CheckAction extends Action {

    public CheckAction() {
        super(ActionEnum.CHECK, "Check");
    }

    public String description() {
        return "checks";
    }
}
