package test.com.poker;

import com.poker.Card;
import com.poker.Deck;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;


public class DeckTest extends TestCase {
    @Test
    public void testDeckSetup() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            Card card = deck.deal();
            Assert.assertNotNull(card);
        }

        try {
            deck.deal();
            Assert.fail("Looking for illegal argument exception");
        } catch (IllegalArgumentException ex) {
            // worked as expected.
        }

    }

    public void testDeckShuffle() {
        Deck deck = new Deck();
        List<Card> cards1 = new ArrayList<Card>();
        for (int i = 0; i < 52; i++) {
            cards1.add(deck.deal());
        }

        deck.shuffle();

        boolean isEqual = true;
        for (int i = 0; i < 52; i++) {
            Card card = deck.deal();
            Card otherCard = cards1.get(i);
            if (card.hashCode() != otherCard.hashCode()) {
                isEqual = false;
                break;
            }
        }

        Assert.assertFalse(isEqual);
    }

    public void testDeckReset() {
        Deck deck = new Deck();
        List<Card> cards1 = new ArrayList<Card>();
        for (int i = 0; i < 52; i++) {
            cards1.add(deck.deal());
        }

        deck.reset();

        boolean isEqual = true;
        for (int i = 0; i < 52; i++) {
            Card card = deck.deal();
            Card otherCard = cards1.get(i);
            if (card.hashCode() != otherCard.hashCode()) {
                isEqual = false;
                break;
            }
        }

        Assert.assertTrue(isEqual);
    }

}