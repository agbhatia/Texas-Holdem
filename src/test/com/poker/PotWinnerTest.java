package test.com.poker;

import com.poker.Deck;
import com.poker.actions.Action;
import com.poker.actions.CallAction;
import com.poker.actions.RaiseAction;
import com.poker.player.Player;
import com.poker.player.RobotPlayer;
import com.poker.pots.Pot;
import com.poker.pots.PotManager;
import com.poker.pots.PotWinner;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PotWinnerTest extends TestCase {
    private PotManager potManager;
    private Player player1;
    private Player player2;
    private Player player3;

    @Override
    public void setUp() {
        potManager = new PotManager(10, 20);
        player1 = new RobotPlayer(1000, "robot1");
        player2 = new RobotPlayer(1000, "robot2");
        player3 = new RobotPlayer(1000, "robot3");

        Deck deck = new Deck();
        player1.setCard(deck.deal(), 0);
        player1.setCard(deck.deal(), 1);

        player2.setCard(deck.deal(), 0);
        player2.setCard(deck.deal(), 1);

        player2.setCard(deck.deal(), 0);
        player2.setCard(deck.deal(), 1);

        player3.setCard(deck.deal(), 0);
        player3.setCard(deck.deal(), 1);


        Action smallBlind = player2.postSmallBlind(10);
        potManager.postSmallBlind(player2, 10);

        Action bigBlind = player3.postBigBlind(20);
        potManager.postBigBlind(player3, 20);

        Action raise800 = new RaiseAction(800);
        player1.processAction(raise800, potManager.getLastBet());
        potManager.addRaise(player1, 800);


        Action callAction2 = new CallAction(790);
        player2.processAction(callAction2, potManager.getLastBet());
        potManager.addCall(player2, 790);

        Action callAction3 = new CallAction(780);
        player3.processAction(callAction3, potManager.getLastBet());
        potManager.addCall(player3, 780);
        potManager.closePotBetting();
    }

    @Test
    public void testChipsIncreased() {
        List<Pot> pots = potManager.getPots();

        Pot mainPot = pots.get(0);

        PotWinner potWinner = new PotWinner(mainPot);
        potWinner.addWinner(player1);
        potWinner.payWinners();
        Assert.assertEquals(2600, player1.getNumChips());
    }

    public void test2Winners() {
        List<Pot> pots = potManager.getPots();

        Pot mainPot = pots.get(0);

        PotWinner potWinner = new PotWinner(mainPot);
        potWinner.addWinner(player2);
        potWinner.addWinner(player3);
        potWinner.payWinners();
        Assert.assertEquals(1400, player2.getNumChips());
        Assert.assertEquals(1400, player3.getNumChips());
        Assert.assertEquals(200, player1.getNumChips());
    }

    public void test3Winners() {
        List<Pot> pots = potManager.getPots();

        Pot mainPot = pots.get(0);

        PotWinner potWinner = new PotWinner(mainPot);
        potWinner.addWinner(player1);
        potWinner.addWinner(player2);
        potWinner.addWinner(player3);
        potWinner.payWinners();
        Assert.assertEquals(1000, player2.getNumChips());
        Assert.assertEquals(1000, player3.getNumChips());
        Assert.assertEquals(1000, player1.getNumChips());
    }

}