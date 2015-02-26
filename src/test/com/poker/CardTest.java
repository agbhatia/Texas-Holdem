package test.com.poker;

import com.poker.Card;

import org.junit.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class CardTest extends TestCase {

    @Test
    public void testConstructor() {
        Card card = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        Assert.assertEquals(card.toString(), "Ah");
    }
}