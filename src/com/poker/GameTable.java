package com.poker;

import com.poker.actions.Action;
import com.poker.actions.ActionEnum;

import java.util.Random;

import com.poker.player.Player;
import com.poker.pots.PotManager;
import com.poker.pots.PotWinner;

import java.util.*;

/**
 * Created by atul on 2/24/15.
 * This class contains most the game flow logic. It handles continuously dealing hands to the players, calculations
 * their possible actions, and processes the action that the player chooses to perform. It continues dealing hands until
 * there is only one player left (Tournament style).
 */
public class GameTable {
    private List<Player> players;
    private List<Player> activePlayers;
    private PotManager potManager;

    private List<Card> tableCards;
    private Deck deck;

    private int smallBlind;
    private int bigBlind;

    private int dealerIndex;
    private Player dealer;

    private int playerToActIndex;
    private Player playerToAct;

    /**
     * We define a game table with the players sitting at the table, the values for
     * the small blind and the big blinds, which we are going to keep constant for
     * the duration of the tournament.
     *
     * @param players: List of players at the table.
     * @param smallBlind: int
     * @param bigBlind: int
     */
    public GameTable(List<Player> players, int smallBlind, int bigBlind) {
        this.players = players;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.tableCards = new ArrayList<Card>();
        activePlayers = new ArrayList<Player>();
        deck = new Deck();
    }

    /**
     * Continue running until we don't have enough players to continue.
     */
    public void run() {
        // Initial dealer index and playerToAct index to -1 because playHand()
        // will increment the dealer index each time it runs to keep switching
        // the dealer position.
        dealerIndex = -1;
        playerToActIndex = -1;

        if (players.size() < 2) {
            throw new IllegalArgumentException("Not enough players passed in.");
        }

        while (playHand()) {
            display("\nStarting next hand\n\n...");

        }

        display("Tournament over!");

        displayAllPlayers();

        if (activePlayers.size() == 1) {
            display("The winner is: " + activePlayers.get(0));
        } else {
            display("The game ended in a weird state with no winner =(");
        }
    }

    /**
     * Handles the game flow of dealing a hand.
     * @return boolean indicating if the game should keep dealing hands.
     */
    private boolean playHand() {
        // Set up all the necessary objects for processing the beginning of a hand. This also sets up the
        // activePlayers in the hand.
        initializeHand();

        // If only one player is active at the table, we don't have enough players.
        if (activePlayers.size() <= 1) {
            return false;
        }

        // Rules are a bit different in heads up play. The dealer posts the small blind instead of the player next
        // to him. Simply move to the next player in this situation.
        if (activePlayers.size() == 2) {
            nextPlayer();
        }
        // Handle the preflop betting round.
        preflopDeal();
        doPreflopBetting();


        // In our defined rounds for this poker game (we have it currently defined to follow the rules of NL holdem),
        // process each round accordingly. Deal cards, let players perform their actions, then cleanup the state
        // at the end of each betting round to prepare for the round.
        for (RoundEnum round : RoundEnum.values()) {
            if (activePlayers.size() <= 1) {
                break;
            }
            dealTableCards(round);
            doBetting();
            cleanupBettingRound();
            displayPots();
        }

        // We either only have one active player in the hand left or we have finished all the betting rounds. Find
        // the winners and pay them accordingly.
        getWinners();
        return true;
    }

    private void cleanupBettingRound() {
        // We need to close the betting round. This involves a lot of work with regards to cleaning up the pot.
        // For example, we need to figure out if we need to create any side pots for players that went all in,
        // and make sure that is all tracked correctly.

        // Reset the potManager so all the pots are fresh.
        potManager.closePotBetting();

        // We clear each players currentBet because in the next street players start again (the min Bet reverts back
        // to the big blind).
        for (Player player : players) {
            player.clearBet();
        }

        // Set the active player to the player right after the dealer (this player is always first to act on every street).
        playerToActIndex = (dealerIndex + 1) % activePlayers.size();
        playerToAct = activePlayers.get(playerToActIndex);
    }

    /**
     * Initialize the hand in preparation for dealing a new one.
     */
    private void initializeHand() {
        // Clear out the cards from the table. The previous hand is over, we are starting a new one.
        tableCards.clear();
        potManager = new PotManager(smallBlind, bigBlind);

        // Set the active players involved in the hand. The criteria
        // is that the player must not be out of $$.
        activePlayers.clear();
        for (Player player : players) {
            // Clear out the players last hand.
            player.clearHand();

            if (player.getNumChips() > 0) {
                activePlayers.add(player);
            }
        }

        // Move the dealer button to the next active payer.
        dealerIndex = (dealerIndex + 1) % activePlayers.size();
        dealer = activePlayers.get(dealerIndex);

        // Shuffle the deck.
        deck.shuffle();

        // The first player to act is the one right after the dealer.
        playerToActIndex = (dealerIndex + 1) % activePlayers.size();
        playerToAct = activePlayers.get(playerToActIndex);
        display(String.format("New hand, %s is the dealer.", dealer));
        displayActivePlayers();
    }

    /**
     * This function prints out the active players in the hand and their current number of chips.
     */
    private void displayActivePlayers() {
        for (Player player : activePlayers) {
            display(String.format("%s (%s)", player, player.getNumChips()));
        }
    }

    /**
     * This function prints out all the players sitting at the table and their current number of chips.
     */
    private void displayAllPlayers() {
        for (Player player : players) {
            display(String.format("%s (%s)", player, player.getNumChips()));
        }
    }

    /**
     * Perform the preflop bets (small blind and big blind) and clean up state after.
     */
    private void doPreflopBetting() {
        postSmallBlind();
        postBigBlind();
        doBetting();
        cleanupBettingRound();
        displayPots();
    }

    /**
     * Post the small blind.
     */
    private void postSmallBlind() {
        processAction(playerToAct, playerToAct.postSmallBlind(smallBlind));
        nextPlayer();
    }

    /**
     * Post the big blind
     */
    private void postBigBlind() {
        processAction(playerToAct, playerToAct.postBigBlind(bigBlind));
        nextPlayer();
    }

    /**
     * Move the next player to act index to the next player (should be called after a player finished acting).
     */
    private void nextPlayer() {
        playerToActIndex = (playerToActIndex + 1) % activePlayers.size();
        playerToAct = activePlayers.get(playerToActIndex);

    }

    /**
     * Deal hole cards to the players. The rules of texas holdem are that we deal two cards to each player.
     */
    private void preflopDeal() {
        for (Player player : activePlayers) {
            for (int i = 0; i < 2; i++) {
                player.setCard(deck.deal(), i);
            }
            if (player.isMyPointOfView()) {
                display("Your cards: " + Arrays.toString(player.getCards()));
            }
        }
    }

    /**
     * Deal the community cards for the given round.
     * @param round: Round obj that has info regarding how many cards should be burned (discarded) and how many should be
     *               dealt.
     */
    private void dealTableCards(RoundEnum round) {
        int numCardsToBurn = round.getCardsToBurn();

        // Discard the burn cards.
        for (int i = 0; i < numCardsToBurn; i++) {
            deck.deal();
        }

        // Deal the number of cards that the round defines. (Flop should deal 3, turn 1, river 1).
        int numCardsToDeal = round.getNumCards();
        for (int i = 0; i < numCardsToDeal; i++) {
            tableCards.add(deck.deal());
        }

        // Show the game state to the players on the console.
        displayDeal(round);
        displayActivePlayers();
    }

    /**
     * Displays all the pots of the given hand and how much $$ they have in them.
     */
    private void displayPots() {
        display(potManager);
    }

    /**
     * Display the cards that were dealt for the given round.
     * @param round
     */
    private void displayDeal(RoundEnum round) {
        display(String.format("Dealing %s: %s", round, tableCards));
    }

    /**
     * Process player actions in the given round. This function does the following:
     * 1. Starts at the current player and finds out the actions that player can perform.
     * 2. Passes those actions to the Player object which then decides on its own which action
     * to perform.
     * 3. Processes the action that the player chose, and sends that information to the pot Manager if the
     * action was one that resulted in $$ being put into the pot.
     * 4. If someone folds, it checks to see if there are other players in the hand still. If only one player is left,
     * that player is the winner of the hand.
     * 5. Does this for every player until the last player has acted -- it also resets this once a player raises, since
     * everyone gets to act again after that.
     */
    private void doBetting() {
        // Let everyone who is an active player act.
        int numPlayers = activePlayers.size();
        while (numPlayers > 0) {
            // Start at the current player to act.
            playerToAct = activePlayers.get(playerToActIndex);

            // 1: Get the available actions for this player.
            List<ActionEnum> availActions = getAvailableActions(playerToAct);

            // 2: Send the actions to the player who will decide action to perform.
            Action actionToPerform = playerToAct.generateAction(availActions, potManager);

            // 3. Process that action accordingly. If there is a bet involved make sure to keep bookkeeping on
            // what the min raises are for the future, etc.
            int amount = actionToPerform.getIncrementAmount();
            playerToAct.processAction(actionToPerform, potManager.getLastBet());
            if (amount > 0) {
                if (actionToPerform.getActionEnum() == ActionEnum.RAISE) {
                    numPlayers = activePlayers.size()-1;
                } else {
                    numPlayers--;
                }
            } else if (actionToPerform.getActionEnum() == ActionEnum.FOLD) {
                // If a player folds, then make sure that there are other active players still left. If only one active
                // player is left, that player is the winner.
                playerToAct.clearHand();
                activePlayers.remove(playerToActIndex);
                playerToActIndex--;
                if (activePlayers.size() == 1) {
                    Player winner = activePlayers.get(0);
                    numPlayers = 0;
                } else {
                    numPlayers--;
                }
            } else {
                numPlayers--;
            }

            // Process the action the player performed.
            processAction(playerToAct, actionToPerform);

            // Move to the next player.
            nextPlayer();
        }
    }

    /**
     * Calculates the winner(s) of the last hand.
     */
    private void getWinners() {
        if (activePlayers.size() == 1) {
            Player winner = activePlayers.get(0);
            int totalPot = potManager.getTotalPot();
            winner.incrementCash(totalPot);
            display(String.format("%s wins the pot (%s chips) -- everyone else is out.", winner, totalPot));
        } else {
            for (Player player : activePlayers) {
                player.evaluateHand(tableCards);
            }

            List<PotWinner> potWinners = potManager.calculateWinners(activePlayers);
            for (PotWinner potWinner : potWinners) {
                potWinner.payWinners();
                display(potWinner);
            }
        }
    }

    /**
     * Takes in a player object, and looks at the current state of the game and player to see what actions
     * the player can take at this point.
     *
     * @param player: Player to act.
     * @return: List of available actions.
     */
    private List<ActionEnum> getAvailableActions(Player player) {
        List<ActionEnum> actionEnums = new ArrayList<ActionEnum>();
        int lastBet = potManager.getLastBet();
        int playerBet = player.getBet();
        int callAmount = lastBet > 0 ? lastBet - player.getBet() : bigBlind;
        ActionEnum lastAction = player.getLastAction();

        // If this player is All In (meaning he has no chips left to bet, they are all already invested in the hand)
        // then he really can't perform any actions. We return the CHECK action here just to make the coder clearer and
        // simpler, but you could also simply just auto-skip the players turn.
        if (player.isAllIn()) {
            actionEnums.add(ActionEnum.CHECK);
            return actionEnums;
        }

        if (areOpponentsAllIn(player)) {
            if ((lastBet == 0) || (playerBet >= lastBet)) {
                actionEnums.add(ActionEnum.CHECK);
                return actionEnums;
            } else {
                actionEnums.add(ActionEnum.CALL);
                actionEnums.add(ActionEnum.FOLD);
                return actionEnums;
            }
        }

        // At any point in a hand, you can fold (unless you are already all in). Even if there is no bet to you,
        // you have the option to still fold.
        actionEnums.add(ActionEnum.FOLD);

        int playerChips = player.getNumChips();


        if (lastBet <= 0) {
            // If there is no bet yet in this current round, the player can either check or raise (we already added fold
            // for all non-all-in cases.
            actionEnums.add(ActionEnum.CHECK);
            actionEnums.add(ActionEnum.RAISE);

        } else {
            // If we are in a situation where this player already bet, but was raised by player B, we have
            // to account for a couple scenarios: 1. player B went all in, but he went all in for less than the min
            // bet. In this case, this player is only allowed to call (cannot raise). 2. If player B put in more
            // than the min bet than we can still raise.
            int betDiff = lastBet - playerBet;
            int betIncrement = potManager.getBetIncrement();
            if ((playerBet < lastBet)) {
                actionEnums.add(ActionEnum.CALL);

                if ((playerBet == 0) || (lastAction == ActionEnum.SMALL_BLIND)) {

                    if (playerChips > (lastBet - playerBet)) {
                        actionEnums.add(ActionEnum.RAISE);
                    }
                } else {
                    if ((betIncrement <= betDiff) && (playerChips > callAmount)) {
                        actionEnums.add(ActionEnum.RAISE);
                    }
                }
            } else if (playerBet == lastBet) {
                actionEnums.add(ActionEnum.CHECK);
                actionEnums.add(ActionEnum.RAISE);
            }
        }

        return actionEnums;
    }

    private boolean areOpponentsAllIn(Player player) {
        for (Player activePlayer : activePlayers) {
            if ((player != activePlayer) && !activePlayer.isAllIn()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Send the action to the pot manager if it resulted in a bet, so the pot manager can keep track of it.
     *
     * @param player: Player that performed the given action
     * @param action: Action
     */
    public void processAction(Player player, Action action) {
        switch(action.getActionEnum()) {
            // If the action is a RAISE, CALL, SMALL_BLIND, or BIG_BLIND a bet was contributed to the pot.
            // Let the pot manager process it accordingly.
            case RAISE:
                potManager.addRaise(player, action.getIncrementAmount());
                break;
            case CALL:
                potManager.addCall(player, action.getIncrementAmount());
                break;
            case SMALL_BLIND:
                potManager.postSmallBlind(player, action.getIncrementAmount());
                break;
            case BIG_BLIND:
                potManager.postBigBlind(player, action.getIncrementAmount());
            default:
                break;
        }
        // Print out the action to the console so the human players can see what happened.
        display(new PlayerAction(player, action));

    }


    private void display(Object obj) {
        System.out.println(obj);
    }
}
