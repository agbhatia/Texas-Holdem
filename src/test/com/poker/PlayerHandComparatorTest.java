package test.com.poker;

import com.poker.Card;
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
        Card[] cards1 = new Card[7];
        cards1[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards1[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards1[2] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards1[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards1[4] = new Card(Card.CardRank.SEVEN, Card.CardSuit.HEARTS);
        cards1[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards1[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Hand hand1 = new Hand(cards1);
        hands.add(hand1);

        Card[] cards2 = new Card[7];
        cards2[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards2[1] = new Card(Card.CardRank.QUEEN, Card.CardSuit.HEARTS);
        cards2[2] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards2[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards2[4] = new Card(Card.CardRank.SEVEN, Card.CardSuit.HEARTS);
        cards2[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards2[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Hand hand2 = new Hand(cards2);
        hands.add(hand2);

        Card[] cards3 = new Card[7];
        cards3[0] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards3[1] = new Card(Card.CardRank.QUEEN, Card.CardSuit.HEARTS);
        cards3[2] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards3[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards3[4] = new Card(Card.CardRank.SEVEN, Card.CardSuit.HEARTS);
        cards3[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards3[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Hand hand3 = new Hand(cards3);
        hands.add(hand3);


        Card[] cards4 = new Card[7];
        cards4[0] = new Card(Card.CardRank.KING, Card.CardSuit.HEARTS);
        cards4[1] = new Card(Card.CardRank.TWO, Card.CardSuit.HEARTS);
        cards4[2] = new Card(Card.CardRank.THREE, Card.CardSuit.HEARTS);
        cards4[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards4[4] = new Card(Card.CardRank.FOUR, Card.CardSuit.HEARTS);
        cards4[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards4[6] = new Card(Card.CardRank.FIVE, Card.CardSuit.HEARTS);
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
        Card[] cards1 = new Card[7];

        cards1[0] = new Card(Card.CardRank.ACE, Card.CardSuit.DIAMONDS);
        cards1[1] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards1[2] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards1[3] = new Card(Card.CardRank.QUEEN, Card.CardSuit.CLUBS);
        cards1[4] = new Card(Card.CardRank.KING, Card.CardSuit.HEARTS);
        cards1[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards1[6] = new Card(Card.CardRank.JACK, Card.CardSuit.SPADES);
        Hand hand1 = new Hand(cards1);
        hands.add(hand1);

        Card[] cards2 = new Card[7];
        cards2[0] = new Card(Card.CardRank.ACE, Card.CardSuit.DIAMONDS);
        cards2[1] = new Card(Card.CardRank.TWO, Card.CardSuit.SPADES);
        cards2[2] = new Card(Card.CardRank.THREE, Card.CardSuit.HEARTS);
        cards2[3] = new Card(Card.CardRank.FOUR, Card.CardSuit.CLUBS);
        cards2[4] = new Card(Card.CardRank.FIVE, Card.CardSuit.HEARTS);
        cards2[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards2[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Hand hand2 = new Hand(cards2);
        hands.add(hand2);

        Card[] cards3 = new Card[7];
        cards3[0] = new Card(Card.CardRank.EIGHT, Card.CardSuit.DIAMONDS);
        cards3[1] = new Card(Card.CardRank.QUEEN, Card.CardSuit.CLUBS);
        cards3[2] = new Card(Card.CardRank.NINE, Card.CardSuit.SPADES);
        cards3[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards3[4] = new Card(Card.CardRank.KING, Card.CardSuit.HEARTS);
        cards3[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards3[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Hand hand3 = new Hand(cards3);
        hands.add(hand3);


        Card[] cards4 = new Card[7];
        cards4[0] = new Card(Card.CardRank.EIGHT, Card.CardSuit.DIAMONDS);
        cards4[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards4[2] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        cards4[3] = new Card(Card.CardRank.TWO, Card.CardSuit.CLUBS);
        cards4[4] = new Card(Card.CardRank.QUEEN, Card.CardSuit.HEARTS);
        cards4[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards4[6] = new Card(Card.CardRank.FIVE, Card.CardSuit.SPADES);
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
        Card[] cards1 = new Card[7];

        cards1[0] = new Card(Card.CardRank.ACE, Card.CardSuit.DIAMONDS);
        cards1[1] = new Card(Card.CardRank.ACE, Card.CardSuit.CLUBS);
        cards1[2] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards1[3] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards1[4] = new Card(Card.CardRank.KING, Card.CardSuit.HEARTS);
        cards1[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards1[6] = new Card(Card.CardRank.JACK, Card.CardSuit.SPADES);
        Hand hand1 = new Hand(cards1);
        hands.add(hand1);

        Card[] cards2 = new Card[7];
        cards2[0] = new Card(Card.CardRank.ACE, Card.CardSuit.DIAMONDS);
        cards2[1] = new Card(Card.CardRank.ACE, Card.CardSuit.SPADES);
        cards2[2] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards2[3] = new Card(Card.CardRank.FOUR, Card.CardSuit.CLUBS);
        cards2[4] = new Card(Card.CardRank.FIVE, Card.CardSuit.HEARTS);
        cards2[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards2[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Hand hand2 = new Hand(cards2);
        hands.add(hand2);

        Card[] cards3 = new Card[7];
        cards3[0] = new Card(Card.CardRank.ACE, Card.CardSuit.DIAMONDS);
        cards3[1] = new Card(Card.CardRank.ACE, Card.CardSuit.CLUBS);
        cards3[2] = new Card(Card.CardRank.FOUR, Card.CardSuit.SPADES);
        cards3[3] = new Card(Card.CardRank.SEVEN, Card.CardSuit.CLUBS);
        cards3[4] = new Card(Card.CardRank.SEVEN, Card.CardSuit.HEARTS);
        cards3[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards3[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Hand hand3 = new Hand(cards3);
        hands.add(hand3);


        Card[] cards4 = new Card[7];
        cards4[0] = new Card(Card.CardRank.TEN, Card.CardSuit.DIAMONDS);
        cards4[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards4[2] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        cards4[3] = new Card(Card.CardRank.THREE, Card.CardSuit.CLUBS);
        cards4[4] = new Card(Card.CardRank.THREE, Card.CardSuit.HEARTS);
        cards4[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards4[6] = new Card(Card.CardRank.FIVE, Card.CardSuit.SPADES);
        Hand hand4 = new Hand(cards4);
        hands.add(hand4);

        Card[] cards5 = new Card[7];
        cards5[0] = new Card(Card.CardRank.TEN, Card.CardSuit.DIAMONDS);
        cards5[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards5[2] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        cards5[3] = new Card(Card.CardRank.TWO, Card.CardSuit.CLUBS);
        cards5[4] = new Card(Card.CardRank.THREE, Card.CardSuit.HEARTS);
        cards5[5] = new Card(Card.CardRank.THREE, Card.CardSuit.CLUBS);
        cards5[6] = new Card(Card.CardRank.TWO, Card.CardSuit.SPADES);
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