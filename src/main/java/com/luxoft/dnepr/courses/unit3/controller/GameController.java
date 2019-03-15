package com.luxoft.dnepr.courses.unit3.controller;

import com.luxoft.dnepr.courses.unit3.model.Card;
import com.luxoft.dnepr.courses.unit3.model.WinState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private static GameController controller;

    private List<Card> deck;
    private List<Card> playerCards;
    private List<Card> dealerCards;

    private GameController() {
        deck = Deck.createDeck(2);
        playerCards = new ArrayList<Card>();
        dealerCards = new ArrayList<Card>();
        // инициализация переменных тут
    }

    public static GameController getInstance() {
        if (controller == null) {
            controller = new GameController();
        }

        return controller;
    }

    public void newGame() {
        newGame(new Shuffler() {

            public void shuffle(List<Card> deck) {
                Collections.shuffle(deck);
            }
        });
    }

    /**
     * Создает новую игру.
     * - перемешивает колоду (используйте для этого shuffler.shuffle(list))
     * - раздает две карты игроку
     * - раздает одну карту диллеру.
     *
     * @param shuffler
     */
    void newGame(Shuffler shuffler) {
        // TODO: реализовать в п5.
        playerCards.clear();
        dealerCards.clear();
        shuffler.shuffle(deck);
        playerCards.add(deck.get(0));
        playerCards.add(deck.get(1));
        dealerCards.add(deck.get(2));
        for (int i = 0; i < 3; i++) {
            deck.remove(0);
        }
    }

    /**
     * Метод вызывается когда игрок запрашивает новую карту.
     * - если сумма очков на руках у игрока больше максимума или колода пуста - ничего не делаем
     * - если сумма очков меньше - раздаем игроку одну карту из коллоды.
     *
     * @return true если сумма очков у игрока меньше максимума (или равна) после всех операций и false если больше.
     */
    public boolean requestMore() {
        if (Deck.costOf(playerCards) > 21 || deck.size() == 0) {
            return false;
        }
        playerCards.add(deck.get(0));
        deck.remove(0);
        if (Deck.costOf(playerCards) <= 21) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Вызывается когда игрок получил все карты и хочет чтобы играло казино (диллер).
     * Сдаем диллеру карты пока у диллера не наберется 17 очков.
     */
    public void requestStop() {
        while (Deck.costOf(dealerCards) <= 17) {
            dealerCards.add(deck.get(0));
            deck.remove(0);
        }
    }

    /**
     * Сравниваем руку диллера и руку игрока.
     * Если у игрока больше максимума - возвращаем WinState.LOOSE (игрок проиграл)
     * Если у игрока меньше чем у диллера и у диллера не перебор - возвращаем WinState.LOOSE (игрок проиграл)
     * Если очки равны - это пуш (WinState.PUSH)
     * Если у игрока больше чем у диллера и не перебор - это WinState.WIN (игрок выиграл).
     */
    public WinState getWinState() {
        int playerPoints = Deck.costOf(playerCards);
        int dealerPoints = Deck.costOf(dealerCards);
        if (playerPoints > 21 || (dealerPoints <= 21 && playerPoints < dealerPoints)) {
            return WinState.LOOSE;
        }
        if (playerPoints == dealerPoints) {
            return WinState.PUSH;
        }
        return WinState.WIN;
    }

    /**
     * Возвращаем руку игрока
     */
    public List<Card> getMyHand() {
        return playerCards;
    }

    /**
     * Возвращаем руку диллера
     */
    public List<Card> getDealersHand() {
        return dealerCards;
    }


}
