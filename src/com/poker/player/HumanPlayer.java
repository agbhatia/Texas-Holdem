package com.poker.player;

import com.poker.actions.*;
import com.poker.pots.PotManager;

import java.util.List;
import java.util.Scanner;

/**
 * Created by atul on 2/25/15.
 * This class implements a human player that prints out available actions for the player when it is his turn to the
 * console. It then processes human's input to perform the action chosen.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(int startingChips, String name) {
        super(startingChips, name);
    }

    public HumanPlayer(int numChips, String playerName, boolean myPointOfView) {
        super(numChips, playerName, myPointOfView);
    }

    /**
     * Ask for human input to console based on the list of actions provided. Keep asking for human input until the
     * human provides a valid input.
     * @param availActions: List of actions that can be performed.
     * @param potManager: The pot manager in case we need info about how much we can raise, etc.
     * @return Action that human to run.
     */
    public Action generateAction(List<ActionEnum> availActions, PotManager potManager) {
        // If the human player has only one option and that is to check, simply perform
        // the check for them. This case happens often when players are all in...no need
        // to keep entering in a check option.
        if ((availActions.size() == 1) && (availActions.get(0) == ActionEnum.CHECK)) {
            return new CheckAction();
        }
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int elementsInSet = availActions.size();

        // String representation of available actions to be shown on console.
        for (int i = 0; i < elementsInSet; i++) {
            sb.append(String.format("(%s)%s ", i + 1, availActions.get(i)));
        }

        System.out.println(this.getBettingInfo(potManager));

        while (true) {
            // Ask for input and wait until user provides some.
            System.out.println(sb.toString());
            String[] input = scanner.nextLine().split("\\s");

            // Keep checking for valid user input. As soon as the input is valid, return out of the while loop with
            // the corresponding action.
            try {
                int index = getOptionNum(input, elementsInSet);
                int amount = getAmount(input);
                ActionEnum actionEnum = availActions.get(index - 1);

                switch (actionEnum) {
                    // Get the details of what the user entered and make sure the action and amounts are valid based
                    // on this player's state.
                    case RAISE:
                        int minRaiseAmount = potManager.getMinimumBetAmount() - bet;
                        int callAmt = potManager.getLastBet() - bet;
                        validateRaise(amount, minRaiseAmount, callAmt);
                        return new RaiseAction(amount);
                    case FOLD:
                        validateNonRaiseActions(input);
                        return new FoldAction();
                    case CALL:
                        validateNonRaiseActions(input);
                        int amt = potManager.getLastBet() - bet;
                        amt = (amt >= numChips) ? numChips : amt;
                        return new CallAction(amt);
                    case CHECK:
                        validateNonRaiseActions(input);
                        return new CheckAction();
                    default:
                        throw new IllegalArgumentException("Invalid action enum passed in");
                }
            } catch (NumberFormatException ex) {
                System.out.println("ERROR: Unable to get integer " + ex.getMessage().toLowerCase() + "\nPlease try again...");
            }
            catch (IllegalArgumentException ex) {
                System.out.println("ERROR: " + ex.getMessage() + "\nPlease try again...");
            }
        }

    }

    /**
     * Get the action that the user chose
     * @param words: String[] of user's input.
     * @param maxIndex: The number of options we provided.
     * @return: The index corresponding to one of our options.
     * @throws java.lang.IllegalArgumentException if the index is unable to be calculated correctly due to faulty
     * input.
     */
    private int getOptionNum(String[] words, int maxIndex) {
        if (words.length < 1) {
            throw new IllegalArgumentException("Please enter in a valid input.");
        }
        int index = Integer.parseInt(words[0]);
        if ((index < 1) || (index > maxIndex)) {
            throw new IllegalArgumentException(index + " is not a valid option. Please try again...");
        }

        return index;
    }

    /**
     * For actions that require an amount, check the user input for the amount.
     * @param words: String[] of user's input.
     * @return int: amount
     */
    private int getAmount(String[] words)
    {
        if (words.length < 2) {
            return 0;
        } else {
            return Integer.parseInt(words[1]);
        }
    }

    private void validateNonRaiseActions(String[] input) {
        if (input.length > 1) {
            throw new IllegalArgumentException("For this action you only should be passing in the correct option number.");
        }
    }

    /**
     * If the user wants the raise we need to validate that the amount he wants to raise is a valid amount.
     * If not, print out precise instructions on why.
     * @param amount: int amount that user entered.
     * @param minRaiseAmount: int min amount necessary for a raise.
     * @param callAmount: int amount for simply a call (used to check for valid raises in all in situations).
     */
    private void validateRaise(int amount, int minRaiseAmount, int callAmount) {
        // Can't raise <= 0
        if (amount <= 0) {
            throw new IllegalArgumentException("You have to raise a non-zero amount. Please enter in an amount to raise.");
        }

        // Can't raise more chips than we have
        if (numChips < amount) {
            throw new IllegalArgumentException("You only have " + numChips + " chips left. Please only bet up to this amount.");
        }

        // If our full number of chips is less than the minimum raise amount, we need to check for some valid raise
        // amounts where the raise might still be less than the min raise.
        if (numChips < minRaiseAmount) {
            // If our number of chips is less than even the amount to call, we can't perform a raise in the first place.
            // This should only occur if there is a bug in our code since we shouldn't allow for this option then.
            if (numChips <= callAmount) {
                throw new IllegalArgumentException("You don't have enough chips to raise");
            }

            // If we have less than the min raise amount, we either can't raise or we have to go all in. We can't be
            // raising less than our total number of chips.
            if (numChips > amount) {
                throw new IllegalArgumentException("If you want to raise, you will need to go all in because you don't" +
                        "have enough chips for the minimum raise");
            }
        } else if (amount < minRaiseAmount) {
            // If we have enough chips to raise the min amount we can't let the user raise less than that amount.
            throw new IllegalArgumentException("You have to raise at least " + minRaiseAmount);
        }

    }
}
