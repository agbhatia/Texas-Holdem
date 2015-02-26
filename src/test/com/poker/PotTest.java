package test.com.poker;

import com.poker.actions.Action;
import com.poker.player.Player;
import com.poker.player.RobotPlayer;
import com.poker.pots.Pot;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PotTest extends TestCase {

    @Test
    public void testPotBasic() {
        Pot pot = new Pot("Main");
        Assert.assertEquals(0, pot.getCurrentBet());
        Assert.assertEquals(0, pot.getTotalBet());
        Assert.assertTrue(pot.getPlayers().isEmpty());

        for (int i = 0; i < 5; i++) {
            pot.addPlayer(new RobotPlayer(1000, "robot_" + i));
        }

        Assert.assertEquals(5, pot.getPlayers().size());
    }

}