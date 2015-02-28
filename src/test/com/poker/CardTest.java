package test.com.poker;

import com.poker.Card;

import com.poker.RankEnum;
import com.poker.SuitEnum;
import org.junit.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class CardTest extends TestCase {

    @Test
    public void testConstructor() {
        Card card = new Card(RankEnum.ACE, SuitEnum.HEARTS);
        Assert.assertEquals(card.toString(), "Ah");
    }
}