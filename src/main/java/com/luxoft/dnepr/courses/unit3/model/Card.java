package com.luxoft.dnepr.courses.unit3.model;

public class Card {
    private Rank rank;
    private Suit suit;

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getCost() {
        return getRank().getCost();
    }
}
