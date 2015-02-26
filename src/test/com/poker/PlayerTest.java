package test.com.poker;

import com.poker.player.HumanPlayer;
import com.poker.player.Player;
import com.poker.player.RobotPlayer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest extends TestCase {

    @Test
    public void humanBasicTests() {
        Player human = new HumanPlayer(1000, "Atul");
        Assert.assertEquals(1000, human.getNumChips());

        human.bet(600);
        Assert.assertEquals(600, human.getBet());
        Assert.assertEquals(0, human.getBetIncrement());
        Assert.assertEquals(400, human.getNumChips());

        human.bet(50);
        Assert.assertEquals(650, human.getBet());
        Assert.assertEquals(0, human.getBetIncrement());
        Assert.assertEquals(350, human.getNumChips());

        try {
            human.bet(360);
            Assert.fail("Should not be able to bet more than current number of chips");
        } catch (IllegalArgumentException ex) {
            // all good
        }
    }

    @Test
    public void robotBasicTests() {
        Player robot = new RobotPlayer(400, "Robot");
        Assert.assertEquals(400, robot.getNumChips());

        robot.bet(80);
        Assert.assertEquals(80, robot.getBet());
        Assert.assertEquals(0, robot.getBetIncrement());
        Assert.assertEquals(320, robot.getNumChips());

        robot.bet(100);
        Assert.assertEquals(180, robot.getBet());
        Assert.assertEquals(0, robot.getBetIncrement());
        Assert.assertEquals(220, robot.getNumChips());

        try {
            robot.bet(290);
            Assert.fail("Should not be able to bet more than current number of chips");
        } catch (IllegalArgumentException ex) {
            // all good
        }

        robot.bet(220);
        Assert.assertEquals(400, robot.getBet());
        Assert.assertEquals(0, robot.getBetIncrement());
        Assert.assertEquals(0, robot.getNumChips());
    }

    @Test
    public void testPerformAction() {
        Player robot = new RobotPlayer(400, "Robot");

    }

}