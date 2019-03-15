package com.luxoft.dnepr.courses.unit3.controller;

import java.util.List;

import com.luxoft.dnepr.courses.unit3.model.Card;

interface Shuffler {
    void shuffle(List<Card> deck);
}
