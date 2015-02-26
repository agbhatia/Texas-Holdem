package com.poker.player;

import com.poker.actions.*;
import com.poker.pots.PotManager;

import java.util.List;
import java.util.Random;

/**
 * Created by atul on 2/24/15.
 * This class implements a robot player for the human player to play against. It is not coded to be intelligent at all.
 * Rather it just randomly selects actions to perform.
 */
public class RobotPlayer extends Player {

    public RobotPlayer(int startingChips, String name) {
        super(startingChips, name);
    }

    /**
     * From the list of actions that the player can perform, we randomly choose one.
     * @param allowableActions: List of actions that the player can perform.
     * @param potManager: The pot manager in case we need info about how much we can raise, etc.
     * @return
     */
    public Action generateAction(List<ActionEnum> allowableActions, PotManager potManager) {

        // The robot will always call if calling is an option. If calling is not an option the robot will check
        // to see if checking is an option, in which case it will perform that then. If neither of those two are
        // available, the robot will randomly choose an action.
        boolean hasCallOption = false;
        boolean hasCheckOption = false;
        for (ActionEnum actionEnum : allowableActions) {
            if (actionEnum == ActionEnum.CALL) {
                hasCallOption = true;
                break;
            }

            if (actionEnum == ActionEnum.CHECK) {
                hasCheckOption = true;
            }
        }

        // Check if call option is avail and use that, else if check option is avail use that, else choose random.
        if (hasCallOption) {
            lastAction = ActionEnum.CALL;
        } else if (hasCheckOption) {
            lastAction = ActionEnum.CHECK;
        } else {
            // Randomly choose element based on size of action list.
            int elementsInSet = allowableActions.size();
            int index;
            if (elementsInSet > 1) {
                index = (new Random()).nextInt(elementsInSet);
            } else {
                index = 0;
            }
            lastAction = allowableActions.get(index);
        }

        // Based on the type of action we choose we randomly decide on amounts that are within the confines of legality.
        // Return the correct action based on the type we chose.
        switch (lastAction) {
            case CHECK:
                return new CheckAction();
            case RAISE:
                // If we chose a raise action choose a random number that is greater than the min raise amount if we
                // have more than that number of chips. else choose all of our chips.
                int minBet = potManager.getMinimumBetAmount() - bet;
                if (minBet >= numChips) {
                    // Calculate the min amount for a call. If we have less than the amount for a call we did something
                    // wrong when calculating the available actions.
                    int diff = potManager.getLastBet() - bet;
                    if (diff >= numChips) {
                        throw new IllegalArgumentException("This should have never been allowed");
                    } else {
                        // push in all our chips for less than the min raise.
                        return new RaiseAction(numChips);
                    }
                } else {
                    // Choose a random amount to raise.
                    int randomBetAmount = (new Random()).nextInt(numChips - minBet) + minBet;
                    return new RaiseAction(randomBetAmount);
                }
            case FOLD:
                return new FoldAction();
            case CALL:
                // If we decide to call we either call the full amount or up to the number of chips we have.
                int amt = potManager.getLastBet()-bet;
                if (amt >= numChips) {
                    amt = numChips;
                }
                return new CallAction(amt);
            default:
                throw new IllegalArgumentException("Invalid action enum passed in");
        }
    }
}
