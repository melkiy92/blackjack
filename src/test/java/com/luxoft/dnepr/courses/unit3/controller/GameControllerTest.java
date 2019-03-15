package com.luxoft.dnepr.courses.unit3.controller;

import com.luxoft.dnepr.courses.unit3.model.Card;
import com.luxoft.dnepr.courses.unit3.model.Rank;
import com.luxoft.dnepr.courses.unit3.model.Suit;
import com.luxoft.dnepr.courses.unit3.model.WinState;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

public class GameControllerTest {

    @Before
    // ���������� ����, ������� ������� ���������� ��� ������� ����� (����� ��� �����������)
    public void prepare() throws Exception {
        Field fld = GameController.class.getDeclaredField("controller");
        try {
            fld.setAccessible(true);
            fld.set(null, null);
        } finally {
            fld.setAccessible(false);
        }
    }

    @Test
    public void testGetInstance() {
        assertSame(GameController.getInstance(), GameController.getInstance());
    }

    @Test
    public void testBeforeNewGame() {
        assertTrue(GameController.getInstance().getMyHand().isEmpty());
        assertTrue(GameController.getInstance().getDealersHand().isEmpty());

        assertEquals(WinState.PUSH, GameController.getInstance().getWinState());
    }

    @Test
    public void testAfterNewGame() {
        GameController.getInstance().newGame();
        assertEquals(2, GameController.getInstance().getMyHand().size());
        assertEquals(1, GameController.getInstance().getDealersHand().size());
    }

    @Test
    public void testRequestMore() {
        GameController.getInstance().newGame(new Shuffler() {

            public void shuffle(List<Card> deck) {
                //deck.set(0, new Card(Rank.RANK_10, Suit.SPADES));
                //deck.set(1, new Card(Rank.RANK_ACE, Suit.SPADES));
                //deck.set(2, new Card(Rank.RANK_JACK, Suit.SPADES));
                //deck.set(3, new Card(Rank.RANK_6, Suit.SPADES));
                //deck.set(4, new Card(Rank.RANK_9, Suit.SPADES));
                //deck.set(5, new Card(Rank.RANK_QUEEN, Suit.SPADES));


            }

        });

        //fail("Not implemented yet");
    }

    @Test
    public void testRequestStop() {
        GameController.getInstance().newGame(new Shuffler() {

            public void shuffle(List<Card> deck) {
                //deck.set(0, new Card(Rank.RANK_10, Suit.SPADES));
                //deck.set(1, new Card(Rank.RANK_ACE, Suit.SPADES));
                //deck.set(2, new Card(Rank.RANK_JACK, Suit.SPADES));
                //deck.set(3, new Card(Rank.RANK_9, Suit.SPADES));


            }

        });
        //fail("Not implemented yet");
    }
}
