package test.com.poker;

import com.poker.Card;
import com.poker.RankEnum;
import com.poker.SuitEnum;
import com.poker.hands.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.FIVE, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.SIX, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.SIX, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.TWO, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Collections.sort(cards);


        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.ONE_PAIR, output.getResult());
    }

    @Test
    public void handEvaluatorTestStraight() {
        HandEvaluator e = new OnePairEvaluator();
        HandEvaluator s = new StraightEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.SEVEN, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.SIX, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.EIGHT, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        Collections.sort(cards);

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
        Assert.assertEquals(HandResult.STRAIGHT, output.getResult());
    }

    @Test
    public void handEvaluatorTestStraightWheel() {
        HandEvaluator s = new StraightEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.FIVE  , SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.FOUR, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.THREE, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.TWO, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        Collections.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.STRAIGHT, output.getResult());
    }

    @Test
    public void testFullHouse() {
        HandEvaluator e = new FullHouseEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.SIX, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.SIX, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Collections.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.FULL_HOUSE, output.getResult());
    }

    @Test
    public void testTwoPair() {
        HandEvaluator e = new TwoPairEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.SIX, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.SIX, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Collections.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.TWO_PAIR, output.getResult());
    }

    @Test
    public void testThreeOfAKind() {
        HandEvaluator e = new ThreeOfAKindEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.SIX, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Collections.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.THREE_OF_A_KIND, output.getResult());
    }

    @Test
    public void testFourOfAKind() {
        HandEvaluator e = new FourOfAKindEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Collections.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.FOUR_OF_A_KIND, output.getResult());
    }

    @Test
    public void testFlush() {
        HandEvaluator e = new FlushEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.SEVEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Collections.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.FLUSH, output.getResult());
    }

    @Test
    public void testHighCard() {
        HandEvaluator e = new HighCardEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.DIAMONDS));
        cards.add(new Card(RankEnum.THREE, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.SEVEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TWO, SuitEnum.CLUBS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        Collections.sort(cards);

        HandEvalResult result = e.evaluate(cards);
        Assert.assertTrue(result.isMatch());

        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.HIGH_CARD, output.getResult());
    }

    @Test
    public void handEvaluatorTestStraightFlush() {
        HandEvaluator s = new StraightFlushEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.SEVEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.SIX, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.EIGHT, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.NINE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        Collections.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.STRAIGHT_FLUSH, output.getResult());
    }

    @Test
    public void handEvaluatorTestStraightFlushWheel() {
        HandEvaluator s = new StraightFlushEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.FIVE  , SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.FOUR, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.THREE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TWO, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        Collections.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.STRAIGHT_FLUSH, output.getResult());
    }

    @Test
    public void handEvaluatorTestRoyalFlush() {
        HandEvaluator s = new StraightFlushEvaluator();

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(RankEnum.ACE, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.KING  , SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.FOUR, SuitEnum.SPADES));
        cards.add(new Card(RankEnum.QUEEN, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.JACK, SuitEnum.HEARTS));
        cards.add(new Card(RankEnum.TEN, SuitEnum.CLUBS));
        Collections.sort(cards);

        HandEvalResult result = s.evaluate(cards);
        Assert.assertTrue(result.isMatch());
        Assert.assertEquals(HandResult.ROYAL_FLUSH, result.getResult());
        HandEvalResult output = null;
        for (HandEvaluator handEvaluator : handEvaluators) {
            output = handEvaluator.evaluate(cards);
            if (output.isMatch()) break;
        }
        Assert.assertTrue(output.isMatch());
        Assert.assertEquals(HandResult.ROYAL_FLUSH, output.getResult());
    }
}