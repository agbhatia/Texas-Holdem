package test.com.poker;

import com.poker.Deck;
import com.poker.actions.Action;
import com.poker.actions.CallAction;
import com.poker.actions.RaiseAction;
import com.poker.player.Player;
import com.poker.player.RobotPlayer;
import com.poker.pots.Pot;
import com.poker.pots.PotManager;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PotManagerTest extends TestCase {

    @Test
    public void testPotDivision() {
        PotManager potManager = new PotManager(10, 20);
        Player robot1 = new RobotPlayer(1000, "robot1");
        Player robot2 = new RobotPlayer(500, "robot2");
        Player robot3 = new RobotPlayer(300, "robot3");
        Player robot4 = new RobotPlayer(170, "robot4");

        Deck deck = new Deck();
        robot1.setCard(deck.deal(), 0);
        robot1.setCard(deck.deal(), 1);

        robot2.setCard(deck.deal(), 0);
        robot2.setCard(deck.deal(), 1);

        robot2.setCard(deck.deal(), 0);
        robot2.setCard(deck.deal(), 1);

        robot3.setCard(deck.deal(), 0);
        robot3.setCard(deck.deal(), 1);

        robot4.setCard(deck.deal(), 0);
        robot4.setCard(deck.deal(), 1);

        Action smallBlind = robot2.postSmallBlind(10);
        potManager.postSmallBlind(robot2, 10);

        Action bigBlind = robot3.postBigBlind(20);
        potManager.postBigBlind(robot3, 20);

        Action raise1000 = new RaiseAction(1000);
        robot1.processAction(raise1000, potManager.getLastBet());
        potManager.addRaise(robot1, 1000);

        Action callAction = new CallAction(170);
        robot4.processAction(callAction, potManager.getLastBet());
        potManager.addCall(robot4, 170);

        Action callAction2 = new CallAction(robot2.getNumChips());
        robot2.processAction(callAction2, potManager.getLastBet());
        potManager.addCall(robot2, callAction2.getIncrementAmount());

        Action callAction3 = new CallAction(robot3.getNumChips());
        robot3.processAction(callAction3, potManager.getLastBet());
        potManager.addCall(robot3, callAction3.getIncrementAmount());
        potManager.closePotBetting();

        List<Pot> pots = potManager.getPots();
        Assert.assertEquals(pots.size(), 4);

        Pot mainPot = pots.get(0);
        Assert.assertEquals(170*4, mainPot.getTotalBet());

        Pot sidePot1 = pots.get(1);
        Assert.assertEquals(130*3, sidePot1.getTotalBet());

        Pot sidePot2 = pots.get(2);
        Assert.assertEquals(200*2, sidePot2.getTotalBet());

        Pot sidePot3 = pots.get(3);
        Assert.assertEquals(500, sidePot3.getTotalBet());

    }

    @Test
    public void testPotDivision2() {
        PotManager potManager = new PotManager(10, 20);
        Player robot1 = new RobotPlayer(1000, "robot1");
        Player robot2 = new RobotPlayer(1000, "robot2");
        Player robot3 = new RobotPlayer(1000, "robot3");
        Player robot4 = new RobotPlayer(300, "robot4");

        Deck deck = new Deck();
        robot1.setCard(deck.deal(), 0);
        robot1.setCard(deck.deal(), 1);

        robot2.setCard(deck.deal(), 0);
        robot2.setCard(deck.deal(), 1);

        robot2.setCard(deck.deal(), 0);
        robot2.setCard(deck.deal(), 1);

        robot3.setCard(deck.deal(), 0);
        robot3.setCard(deck.deal(), 1);

        robot4.setCard(deck.deal(), 0);
        robot4.setCard(deck.deal(), 1);

        Action smallBlind = robot2.postSmallBlind(10);
        potManager.postSmallBlind(robot2, 10);

        Action bigBlind = robot3.postBigBlind(20);
        potManager.postBigBlind(robot3, 20);

        Action raise800 = new RaiseAction(800);
        robot1.processAction(raise800, potManager.getLastBet());
        potManager.addRaise(robot1, 800);

        Action callAction = new CallAction(300);
        robot4.processAction(callAction, potManager.getLastBet());
        potManager.addCall(robot4, 300);

        Action callAction2 = new CallAction(790);
        robot2.processAction(callAction2, potManager.getLastBet());
        potManager.addCall(robot2, 790);

        Action callAction3 = new CallAction(780);
        robot3.processAction(callAction3, potManager.getLastBet());
        potManager.addCall(robot3, 780);
        potManager.closePotBetting();

        List<Pot> pots = potManager.getPots();
        Assert.assertEquals(pots.size(), 2);

        Pot mainPot = pots.get(0);
        Assert.assertEquals(300*4, mainPot.getTotalBet());

        Pot sidePot1 = pots.get(1);
        Assert.assertEquals(500*3, sidePot1.getTotalBet());

    }
}