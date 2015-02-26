package test.com.poker;

import com.poker.Card;
import com.poker.hands.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HandEvaluatorTest {

    private static final List<HandEvaluator> handEvaluators = new ArrayList<HandEvaluator>() {{
        add(new StraightFlushEvaluator());
        add(new FourOfAKindEvaluator());
        add(new FullHouseEvaluator());
        add(new FlushEvaluator());
        add(new StraightEvaluator());
        add(new ThreeOfAKindEvaluator());
        add(new TwoPairEvaluator());
        add(new OnePairEvaluator());
        add(new HighCardEvaluator());
    }};

    @Test
    public void handEvaluatorTestOnePair() {
        HandEvaluator e = new OnePairEvaluator();
        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.FIVE, Card.CardSuit.SPADES);
        cards[3] = new Card(Card.CardRank.SIX, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.SIX, Card.CardSuit.DIAMONDS);
        cards[5] = new Card(Card.CardRank.TWO, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Arrays.sort(cards);


        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.ONE_PAIR, output.result());
    }

    @Test
    public void handEvaluatorTestStraight() {
        HandEvaluator e = new OnePairEvaluator();
        HandEvaluator s = new StraightEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.SEVEN, Card.CardSuit.SPADES);
        cards[3] = new Card(Card.CardRank.SIX, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.EIGHT, Card.CardSuit.DIAMONDS);
        cards[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        Arrays.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.STRAIGHT, output.result());
    }

    @Test
    public void handEvaluatorTestStraightWheel() {
        HandEvaluator s = new StraightEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.SPADES);
        cards[2] = new Card(Card.CardRank.FIVE  , Card.CardSuit.HEARTS);
        cards[3] = new Card(Card.CardRank.FOUR, Card.CardSuit.HEARTS);
        cards[4] = new Card(Card.CardRank.THREE, Card.CardSuit.CLUBS);
        cards[5] = new Card(Card.CardRank.TWO, Card.CardSuit.DIAMONDS);
        cards[6] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        Arrays.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.STRAIGHT, output.result());
    }

    @Test
    public void testFullHouse() {
        HandEvaluator e = new FullHouseEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.TEN, Card.CardSuit.SPADES);
        cards[3] = new Card(Card.CardRank.SIX, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.SIX, Card.CardSuit.DIAMONDS);
        cards[5] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Arrays.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.FULL_HOUSE, output.result());
    }

    @Test
    public void testTwoPair() {
        HandEvaluator e = new TwoPairEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.TEN, Card.CardSuit.SPADES);
        cards[3] = new Card(Card.CardRank.SIX, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.SIX, Card.CardSuit.DIAMONDS);
        cards[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Arrays.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.TWO_PAIR, output.result());
    }

    @Test
    public void testThreeOfAKind() {
        HandEvaluator e = new ThreeOfAKindEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.TEN, Card.CardSuit.SPADES);
        cards[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.SIX, Card.CardSuit.DIAMONDS);
        cards[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Arrays.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.THREE_OF_A_KIND, output.result());
    }

    @Test
    public void testFourOfAKind() {
        HandEvaluator e = new FourOfAKindEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.TEN, Card.CardSuit.SPADES);
        cards[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.TEN, Card.CardSuit.DIAMONDS);
        cards[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Arrays.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.FOUR_OF_A_KIND, output.result());
    }

    @Test
    public void testFlush() {
        HandEvaluator e = new FlushEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards[3] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.SEVEN, Card.CardSuit.HEARTS);
        cards[5] = new Card(Card.CardRank.NINE, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Arrays.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.FLUSH, output.result());
    }

    @Test
    public void testHighCard() {
        HandEvaluator e = new HighCardEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.NINE, Card.CardSuit.DIAMONDS);
        cards[3] = new Card(Card.CardRank.THREE, Card.CardSuit.CLUBS);
        cards[4] = new Card(Card.CardRank.SEVEN, Card.CardSuit.HEARTS);
        cards[5] = new Card(Card.CardRank.TWO, Card.CardSuit.CLUBS);
        cards[6] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        Arrays.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.HIGH_CARD, output.result());
    }

    @Test
    public void handEvaluatorTestStraightFlush() {
        HandEvaluator s = new StraightFlushEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.SPADES);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.SEVEN, Card.CardSuit.HEARTS);
        cards[3] = new Card(Card.CardRank.SIX, Card.CardSuit.HEARTS);
        cards[4] = new Card(Card.CardRank.EIGHT, Card.CardSuit.HEARTS);
        cards[5] = new Card(Card.CardRank.NINE, Card.CardSuit.HEARTS);
        cards[6] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        Arrays.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.STRAIGHT_FLUSH, output.result());
    }

    @Test
    public void handEvaluatorTestStraightFlushWheel() {
        HandEvaluator s = new StraightFlushEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.SPADES);
        cards[2] = new Card(Card.CardRank.FIVE  , Card.CardSuit.HEARTS);
        cards[3] = new Card(Card.CardRank.FOUR, Card.CardSuit.HEARTS);
        cards[4] = new Card(Card.CardRank.THREE, Card.CardSuit.HEARTS);
        cards[5] = new Card(Card.CardRank.TWO, Card.CardSuit.HEARTS);
        cards[6] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        Arrays.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.STRAIGHT_FLUSH, output.result());
    }

    @Test
    public void handEvaluatorTestRoyalFlush() {
        HandEvaluator s = new StraightFlushEvaluator();

        Card[] cards = new Card[7];
        cards[0] = new Card(Card.CardRank.ACE, Card.CardSuit.HEARTS);
        cards[1] = new Card(Card.CardRank.TEN, Card.CardSuit.HEARTS);
        cards[2] = new Card(Card.CardRank.KING  , Card.CardSuit.HEARTS);
        cards[3] = new Card(Card.CardRank.FOUR, Card.CardSuit.SPADES);
        cards[4] = new Card(Card.CardRank.QUEEN, Card.CardSuit.HEARTS);
        cards[5] = new Card(Card.CardRank.JACK, Card.CardSuit.HEARTS);
        cards[6] = new Card(Card.CardRank.TEN, Card.CardSuit.CLUBS);
        Arrays.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        Assert.assertEquals(HandResult.ROYAL_FLUSH, result.result());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.ROYAL_FLUSH, output.result());
    }
}