package com.poker.player;

import com.poker.Card;
import com.poker.actions.Action;
import com.poker.actions.ActionEnum;
import com.poker.actions.BigBlindAction;
import com.poker.actions.SmallBlindAction;
import com.poker.hands.Hand;
import com.poker.pots.PotManager;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by atul on 2/25/15.
 * This is an abstract class to define players. A lot of the functionality is common, so we define the functions here.
 */
public abstract class Player implements Comparable<Player> {

    protected int numChips;
    private Card[] cards = new Card[2];
    protected int bet;
    protected int betIncrement;
    protected ActionEnum lastAction;
    private String playerName;
    protected boolean isInHand;
    protected boolean myPointOfView;
    private Hand hand;

    /**
     * Set up a user with a number of chips and a given name.
     * @param numChips
     * @param playerName
     */
    public Player(int numChips, String playerName) {
        this(numChips, playerName, false);
    }

    /**
     * Set up player indicating if it is from this player's point of view;
     * @param numChips
     * @param playerName
     * @param myPointOfView
     */
    public Player(int numChips, String playerName, boolean myPointOfView) {
        this.numChips = numChips;
        this.playerName = playerName;
        this.myPointOfView = myPointOfView;
    }

    public void setCards(List<Card> holeCards) {
        int numCards = holeCards.size();
        for (int i = 0; i < numCards; i++) {
            cards[i] = holeCards.get(i);
        }
    }

    /**
     * Set one of the hole cards for a player.
     * @param card: Card to deal to player
     * @param cardNum: int position of hole card.
     */
    public void setCard(Card card, int cardNum) {

        cards[cardNum] = card;
        isInHand = true;
    }

    public int getNumChips() {
        return numChips;
    }

    public Card[] getCards() {
        return cards;
    }

    public boolean isMyPointOfView() {
        return myPointOfView;
    }

    /**
     * Clear out this users hand.
     */
    public void clearHand() {
        cards = new Card[2];
        clearBet();
        isInHand = false;
    }

    /**
     * A user is all in when he is still in the hand but his number of chips is set to 0.
     * @return
     */
    public boolean isAllIn() {
        return (numChips == 0) && isInHand;
    }

    /**
     * In between betting rounds (from flop to turn) we clear out the users bet since betting starts anew on each
     * street.
     */
    public void clearBet() {
        bet = 0;
        // Only set last action to null for players still in the hand. We want to keep track of players that have
        // folded, etc.
        if (isInHand) {
            lastAction = null;
        };
    }

    /**
     * @return boolean indicating if player has ability to be an active bettor in this hand.
     */
    public boolean isActive() {
        // If the user is in the hand and last action is not null this means that the player is still active.
        // This function is checked at the end of a betting round so each player will have gotten to act, so
        // the only players that are in the hand but have a null last action are those that went all in the
        // previous betting round (or earlier).
        return isInHand && (lastAction != null);
    }

    /**
     * Bet a certain amount of chips. Keep track of the total bet amount and decrement our cash.
     * @param amount: int amount to bet
     * @throws java.lang.IllegalArgumentException: If we try to bet more chips than we have.
     */
    public void bet(int amount) {
        if (amount > numChips) {
            throw new IllegalArgumentException("Can't bet more than " + numChips + " chips");
        }
        bet += amount;
        decrementCash(amount);
    }

    /**
     * Post a small blind.
     * @param amount: amount to post.
     * @return Action
     */
    public Action postSmallBlind(int amount) {
        lastAction = ActionEnum.SMALL_BLIND;
        int amountToBet = (amount <= numChips) ? amount : numChips;
        bet(amountToBet);
        setBetIncrement(amountToBet);
        return new SmallBlindAction(amountToBet);
    }

    /**
     * Post a big blind.
     * @param amount: amount to post.
     * @return Action
     */
    public Action postBigBlind(int amount) {
        lastAction = ActionEnum.BIG_BLIND;
        int amountToBet = (amount <= numChips) ? amount : numChips;
        bet(amountToBet);
        setBetIncrement(amountToBet);
        return new BigBlindAction(amountToBet);
    }

    /**
     * @return Action: this player's last action in this round. Folds will persist across all rounds.
     */
    public ActionEnum getLastAction() {
        return lastAction;
    }

    public Hand getHand() {
        return hand;
    }

    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction.getActionEnum();
    }

    public int getBet() {
        return bet;
    }

    public int getBetIncrement() {
        return betIncrement;
    }

    public void setBetIncrement(int betIncrement) {
        this.betIncrement = betIncrement;
    }

    public void decrementCash(int amount) {
        if (amount > numChips) {
            throw new IllegalArgumentException("Can't decrement more than " + numChips + " chips");
        }

        numChips -= amount;
    }

    public void incrementCash(int amount) {
        numChips += amount;
    }

    /**
     * Process a given action for this player. Increment the bet amounts / decrement the cash amount
     * if necessary. We need the lastPotBet so that we can set the bet increment appropriately in cases
     * where we raised.
     * @param action
     * @param lastPotBet
     */
    public void processAction(Action action, int lastPotBet) {
        int amount = action.getIncrementAmount();
        if (action.getIncrementAmount() > 0) {
            bet(amount);
            setBetIncrement(getBet() - lastPotBet);
        }
        setLastAction(action);
    }

    public void setHand(List<Card> tableCards) {
        hand = new Hand(getCards(), tableCards);
    }

    public void evaluateHand(List<Card> tableCards) {
        hand = new Hand(getCards(), tableCards);
        hand.evaluate();
    }

    /**
     * Return information about the game state and the player state. Offers more info if the current player is you.
     * @param potManager
     * @return
     */
    protected String getBettingInfo(PotManager potManager) {
        if (myPointOfView) {
            return "My turn!\nMy Cards: " + Arrays.toString(getCards()) + "\n(" + numChips + " chips, Current Bet: "
                    + potManager.getLastBet() + ", My Bet: " + getBet() + ", Total Pot: " + potManager.getTotalPot() +
                    ")";
        } else {
            return this + "'s turn!\n(" + numChips + " chips, Current Bet: " + potManager.getLastBet() +
                    ", " + this + "'s Bet: " + getBet() + ", Pot Size: " + potManager.getTotalPot() + ")";
        }
    }
    /**
     * This function is to be implemented by the extenders. It should decide which action to use out of a given
     * list of actions that are allowed.
     * @param allowableActions: List of actions that the player can perform.
     * @param potManager: The pot manager in case we need info about how much we can raise, etc.
     * @return Action: indicates the action the player decided to choose.
     */
    public abstract Action generateAction(List<ActionEnum> allowableActions, PotManager potManager);

    @Override
    public String toString() {
        return String.format("%s", playerName);

    }

    @Override
    public int compareTo(Player player) {
        int bet1 = this.getBet();
        int bet2 = player.getBet();

        return  bet1 > bet2 ? +1 : bet1 < bet2 ? -1 : 0;
    }
}
