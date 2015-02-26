package test.com.poker;

import com.poker.GameTable;
import com.poker.player.HumanPlayer;
import com.poker.player.Player;
import com.poker.player.RobotPlayer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTableTest extends TestCase {
    @Test
    public void testGameWithOnePlayer() {
        HumanPlayer player2 = new HumanPlayer(7, "Bob");
        List<Player> players = new ArrayList<Player>();
        players.add(player2);
        GameTable gameTable = new GameTable(players, 10, 20);
        try {
            gameTable.run();
            Assert.fail("We did not encounter the exception we were expecting.");
        } catch (IllegalArgumentException ex) {
            // good
        }
    }

    @Test
    public void testGameWithNoPlayers() {
        List<Player> players = new ArrayList<Player>();

        GameTable gameTable = new GameTable(players, 10, 20);
        try {
            gameTable.run();
            Assert.fail("We did not encounter the exception we were expecting.");
        } catch (IllegalArgumentException ex) {
            // good
        }
    }

    @Test
    public void testGameWithEnoughPlayers() {
        List<Player> players = new ArrayList<Player>();

        Player player1 = new RobotPlayer(7, "Bob");
        Player player2 = new RobotPlayer(7, "Steve");

        players.add(player1);
        players.add(player2);

        GameTable gameTable = new GameTable(players, 10, 20);


        try {
            gameTable.run();

        } catch (IllegalArgumentException ex) {
            Assert.fail("Should not be getting an exception this time.");

        }
    }

}