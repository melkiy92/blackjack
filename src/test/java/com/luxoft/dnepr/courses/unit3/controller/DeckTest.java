package com.luxoft.dnepr.courses.unit3.controller;

import com.luxoft.dnepr.courses.unit3.model.Card;
import com.luxoft.dnepr.courses.unit3.model.Rank;
import com.luxoft.dnepr.courses.unit3.model.Suit;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DeckTest {

    @Test
    public void testCreate() {
        assertEquals(52, Deck.createDeck(1).size());
        assertEquals(104, Deck.createDeck(2).size());
        assertEquals(208, Deck.createDeck(4).size());
        assertEquals(52 * 10, Deck.createDeck(10).size());

        assertEquals(52 * 10, Deck.createDeck(11).size());
        assertEquals(52, Deck.createDeck(-1).size());

        List<Card> deck = Deck.createDeck(2);
        int i = 0;

        for (int deckN = 0; deckN < 2; deckN++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    //расскоментировать когда будут готовы п1-п3
                    assertEquals(suit, deck.get(i).getSuit());
                    assertEquals(rank, deck.get(i).getRank());
                    assertEquals(rank.getCost(), deck.get(i).getCost());
                    i++;
                }
            }
        }
    }

    // @Test
    //public void testCostOf() {
    //  fail("Not implemented");
    //}
}
