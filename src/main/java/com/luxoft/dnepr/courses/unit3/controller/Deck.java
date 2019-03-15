package com.luxoft.dnepr.courses.unit3.controller;

import com.luxoft.dnepr.courses.unit3.model.Card;
import com.luxoft.dnepr.courses.unit3.model.Rank;
import com.luxoft.dnepr.courses.unit3.model.Suit;

import java.util.ArrayList;
import java.util.List;

public final class Deck {

    public static List<Card> createDeck(int size) {
        List<Card> deck = new ArrayList<Card>();
        if (size < 1) {
            size = 1;
        }
        if (size > 10) {
            size = 10;
        }

        for (int i = 0; i < size; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    Card card = new Card(rank, suit);
                    deck.add(card);
                }
            }
        }
        return deck;
    }

    public static int costOf(List<Card> deck) {
        int costSum = 0;
        for (Card card : deck) {
            costSum += card.getCost();
        }
        return costSum;
    }


}
