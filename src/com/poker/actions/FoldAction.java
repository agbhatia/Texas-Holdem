package com.poker.actions;

/**
 * Created by atul on 2/24/15.
 */
public class FoldAction extends Action {

    public FoldAction() {
        super(ActionEnum.FOLD, "Fold");
    }

    public String description() {
        return "folds";
    }
}
