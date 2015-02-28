package test.com.poker;

import com.poker.Card;
import com.poker.RankEnum;
import com.poker.SuitEnum;
import com.poker.hands.Hand;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerHandComparatorTest extends TestCase {

    @Test
    /**
     * Test flushes against bigger flushes. We modify the cards so that sometimes the first one is the highest.
     * and other times other ones are causing the diff.
     */
    public void testFlushvsFlush() {
        List<Hand> hands = new ArrayList<Hand>();
        List<Card> cards1 = new ArrayList<>();
        cards1.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.SEVEN, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Hand hand1 = new Hand(cards1);
        hands.add(hand1);

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.QUEEN, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards2.add(new Card(RankEnum.SEVEN, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards2.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Hand hand2 = new Hand(cards2);
        hands.add(hand2);

        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards3.add(new Card(RankEnum.QUEEN, SuitEnum.HEARTS));
        cards3.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards3.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.SEVEN, SuitEnum.HEARTS));
        cards3.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Hand hand3 = new Hand(cards3);
        hands.add(hand3);


        List<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(RankEnum.KING, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.TWO, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.THREE, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards4.add(new Card(RankEnum.FOUR, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards4.add(new Card(RankEnum.FIVE, SuitEnum.HEARTS));
        Hand hand4 = new Hand(cards4);
        hands.add(hand4);

        hands = runThroughHands(hands);
        Assert.assertEquals(hand2, hands.get(0));
        Assert.assertEquals(hand1, hands.get(1));
        Assert.assertEquals(hand4, hands.get(2));
        Assert.assertEquals(hand3, hands.get(3));
    }

    public void testStraightvsStraight() {
        List<Hand> hands = new ArrayList<Hand>();
        List<Card> cards1 = new ArrayList<>();

        cards1.add(new Card(RankEnum.ACE, SuitEnum.DIAMONDS));
        cards1.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.QUEEN, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.KING, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.JACK, SuitEnum.SPADES));
        Hand hand1 = new Hand(cards1);
        hands.add(hand1);

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(RankEnum.ACE, SuitEnum.DIAMONDS));
        cards2.add(new Card(RankEnum.TWO, SuitEnum.SPADES));
        cards2.add(new Card(RankEnum.THREE, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.FOUR, SuitEnum.CLUBS));
        cards2.add(new Card(RankEnum.FIVE, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards2.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Hand hand2 = new Hand(cards2);
        hands.add(hand2);

        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(RankEnum.EIGHT, SuitEnum.DIAMONDS));
        cards3.add(new Card(RankEnum.QUEEN, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.NINE, SuitEnum.SPADES));
        cards3.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.KING, SuitEnum.HEARTS));
        cards3.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Hand hand3 = new Hand(cards3);
        hands.add(hand3);


        List<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(RankEnum.EIGHT, SuitEnum.DIAMONDS));
        cards4.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.TWO, SuitEnum.CLUBS));
        cards4.add(new Card(RankEnum.QUEEN, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards4.add(new Card(RankEnum.FIVE, SuitEnum.SPADES));
        Hand hand4 = new Hand(cards4);
        hands.add(hand4);

        hands = runThroughHands(hands);
        Assert.assertEquals(hand1, hands.get(0));
        Assert.assertEquals(hand3, hands.get(1));
        Assert.assertEquals(hand4, hands.get(2));
        Assert.assertEquals(hand2, hands.get(3));
    }

    public void test2Pairvs2Pair() {
        List<Hand> hands = new ArrayList<Hand>();
        List<Card> cards1 = new ArrayList<>();

        cards1.add(new Card(RankEnum.ACE, SuitEnum.DIAMONDS));
        cards1.add(new Card(RankEnum.ACE, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.KING, SuitEnum.HEARTS));
        cards1.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards1.add(new Card(RankEnum.JACK, SuitEnum.SPADES));
        Hand hand1 = new Hand(cards1);
        hands.add(hand1);

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(RankEnum.ACE, SuitEnum.DIAMONDS));
        cards2.add(new Card(RankEnum.ACE, SuitEnum.SPADES));
        cards2.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.FOUR, SuitEnum.CLUBS));
        cards2.add(new Card(RankEnum.FIVE, SuitEnum.HEARTS));
        cards2.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards2.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Hand hand2 = new Hand(cards2);
        hands.add(hand2);

        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(RankEnum.ACE, SuitEnum.DIAMONDS));
        cards3.add(new Card(RankEnum.ACE, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.FOUR, SuitEnum.SPADES));
        cards3.add(new Card(RankEnum.SEVEN, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.SEVEN, SuitEnum.HEARTS));
        cards3.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards3.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Hand hand3 = new Hand(cards3);
        hands.add(hand3);


        List<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(RankEnum.TEN, SuitEnum.DIAMONDS));
        cards4.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.THREE, SuitEnum.CLUBS));
        cards4.add(new Card(RankEnum.THREE, SuitEnum.HEARTS));
        cards4.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards4.add(new Card(RankEnum.FIVE, SuitEnum.SPADES));
        Hand hand4 = new Hand(cards4);
        hands.add(hand4);

        List<Card> cards5 = new ArrayList<>();
        cards5.add(new Card(RankEnum.TEN, SuitEnum.DIAMONDS));
        cards5.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards5.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        cards5.add(new Card(RankEnum.TWO, SuitEnum.CLUBS));
        cards5.add(new Card(RankEnum.THREE, SuitEnum.HEARTS));
        cards5.add(new Card(RankEnum.THREE, SuitEnum.CLUBS));
        cards5.add(new Card(RankEnum.TWO, SuitEnum.SPADES));
        Hand hand5 = new Hand(cards5);
        hands.add(hand5);

        hands = runThroughHands(hands);
        Assert.assertEquals(hand1, hands.get(0));
        Assert.assertEquals(hand2, hands.get(1));
        Assert.assertEquals(hand3, hands.get(2));

        Hand tmpHand1 = hands.get(3);
        Hand tmpHand2 = hands.get(3);
        Assert.assertEquals(tmpHand1, tmpHand2);

    }

    private List<Hand> runThroughHands(List<Hand> hands) {
        for (Hand hand : hands) {
            hand.evaluate();
        }
        Collections.sort(hands);
        return hands;
    }

}