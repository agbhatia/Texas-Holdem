package com.poker;

import com.poker.player.HumanPlayer;
import com.poker.player.Player;
import com.poker.player.RobotPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Run a texas holdem game. Runs until one of the players is out.
    public static void main(String[] args) {
        // Ask the user to input some values for number of players and the number of chips the players start out with.
        Scanner s = new Scanner(System.in);
        int numPlayers = getNumPlayers(s);
        int startingChips = getStartingChips(s);

        List<Player> players = new ArrayList<Player>();

        // Create 1 human player who has the current View for display purposes.
        Player player1 = new HumanPlayer(startingChips, "Human Player", true);
        players.add(player1);

        // Create robots for all other players.
        for (int i = 0; i < numPlayers - 1; i++) {
            int robotNum = i+1;
            players.add(new RobotPlayer(startingChips, "Robot #" + robotNum));
        }

        // hardcode big blind and small blind for now.
        GameTable table = new GameTable(players, 10, 20);
        table.run();
    }

    /**
     * Get the starting chips from user input.
     * @param scanner
     * @return: Int starting chips.
     */
    private static int getStartingChips(Scanner scanner) {
        while (true) {
            System.out.print("Enter the number of starting chips for the players: ");
            String numChipsString = scanner.next();
            try {
                int numChips = getInteger(numChipsString);
                if (numChips < 20) {
                    System.out.println("Please use at least a minimum of 20 chips.");
                } else {
                    return numChips;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Could not convert your input to a number, please try again...");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    /**
     * Get the number of players from user input.
     * @param scanner
     * @return int number of players
     */
    private static int getNumPlayers(Scanner scanner) {
        while (true) {
            System.out.print("Enter in the number of players (You will get one human to control, " +
                    "the rest will be robots): ");
            String numPlayersString = scanner.next();
            try {
                int numPlayers = getInteger(numPlayersString);
                if (numPlayers < 2) {
                    System.out.println("You must use at least 2 players to start a game.");
                } else {
                    return numPlayers;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Could not convert your input to a number, please try again...");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    /**
     * Get a valid integer from the user's input.
     * @param numPlayersString
     * @return int
     * @throws java.lang.NumberFormatException
     */
    private static int getInteger(String numPlayersString) {
        int returnVal = Integer.parseInt(numPlayersString);
        return returnVal;
    }
}
