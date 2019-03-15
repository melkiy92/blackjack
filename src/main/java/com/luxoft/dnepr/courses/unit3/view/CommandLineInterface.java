package com.luxoft.dnepr.courses.unit3.view;

import com.luxoft.dnepr.courses.unit3.controller.Deck;
import com.luxoft.dnepr.courses.unit3.controller.GameController;
import com.luxoft.dnepr.courses.unit3.model.Card;
import com.luxoft.dnepr.courses.unit3.model.WinState;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {

    private Scanner scanner;
    private PrintStream output;

    public CommandLineInterface(PrintStream output, InputStream input) {
        this.scanner = new Scanner(input);
        this.output = output;
    }

    public void play() {
        output.println("Console Blackjack application.\n" +
                "Author: Trubaieva Anna\n" +
                "(C) Ciklum 2019\n");

        GameController controller = GameController.getInstance();
        controller.newGame();

        output.println();
        printState(controller);

        while (scanner.hasNext()) {
            String command = scanner.next();
            if (!execute(command, controller)) {
                return;
            }
        }

    }

    /**
     * Выполняем команду, переданную с консоли. Список разрешенных комманд можно найти в классе {@link Command}.
     * Используйте методы контроллера чтобы обращаться к логике игры. Этот класс должен содержать только интерфейс.
     * Если этот метод вернет false - игра завершится.
     * <p/>
     * Более детальное описание формата печати можно узнать посмотрев код юниттестов.
     * //@see com.luxoft.dnepr.courses.unit3.view.CommandLineInterfaceTest
     * <p/>
     * Описание команд:
     * Command.HELP - печатает помощь.
     * Command.MORE - еще одну карту и напечатать Состояние (GameController.requestMore())
     * если после карты игрок проиграл - напечатать финальное сообщение и выйти
     * Command.STOP - игрок закончил, теперь играет диллер (GameController.requestStop())
     * после того как диллер сыграл напечатать:
     * Dealer turn:
     * пустая строка
     * состояние
     * пустая строка
     * финальное сообщение
     * Command.EXIT - выйти из игры
     * <p/>
     * Состояние:
     * рука игрока (total вес)
     * рука диллера (total вес)
     * <p/>
     * например:
     * 3 J 8 (total 21)
     * A (total 11)
     * <p/>
     * Финальное сообщение:
     * В зависимости от состояния печатаем:
     * Congrats! You win!
     * Push. Everybody has equal amount of points.
     * Sorry, today is not your day. You loose.
     * <p/>
     * Постарайтесь уделить внимание чистоте кода и разделите этот метод на несколько подметодов.
     */
    private boolean execute(String command, GameController controller) {
        if (Command.HELP.equals(command)) {
            help();
        } else if (Command.MORE.equals(command)) {
            /*if( ! hit(controller) ) {
                return false;
            }*/
            return hit(controller);
        } else if (Command.STOP.equals(command)) {
            stand(controller);
            return false;
        } else if (Command.EXIT.equals(command)) {
            return false;
        }
        return true;
    }

    private void help() {
        output.println("Usage: \n" +
                "\thelp - prints this message\n" +
                "\thit - requests one more card\n" +
                "\tstand - I'm done - lets finish\n" +
                "\texit - exits game");
    }

    private boolean hit(GameController controller) {
        if (controller.requestMore()) {
            printState(controller);
            return true;
        }
        //output.println();
       /* controller.requestMore();
        printState(controller);
        WinState state = controller.getWinState();
        if( state == WinState.LOOSE) {
            //output.println();
            printFinalMessage(state);
            return false;
        }
        return true;*/
        printState(controller);
        output.println();
        printFinalMessage(controller.getWinState());
        return false;

    }


    private void stand(GameController controller) {
        controller.requestStop();
        output.println(); //added
        output.println("Dealer turn:");
        output.println();
        printState(controller);
        output.println();
        printFinalMessage(controller.getWinState());
    }

    private void printFinalMessage(WinState state) {
        switch (state) {
            case LOOSE:
                output.println("Sorry, today is not your day. You loose.");
                break;
            case WIN:
                output.println("Congrats! You win!");
                break;
            case PUSH:
                output.println("Push. Everybody has equal amount of points.");
        }
    }

    private void printState(GameController controller) {
        List<Card> myHand = controller.getMyHand();
        int i;
        String formatMyHand = "";
        for (i = 0; i < myHand.size(); i++) {
            formatMyHand += myHand.get(i).getRank().getName() + " ";
        }
        formatMyHand += "(total " + Deck.costOf(myHand) + ")";
        this.output.println(formatMyHand);
        List<Card> dealersHand = controller.getDealersHand();
        int j;
        String formatdealersHand = "";
        for (j = 0; j < dealersHand.size(); j++) {
            formatdealersHand += dealersHand.get(j).getRank().getName() + " ";
        }
        formatdealersHand += "(total " + Deck.costOf(dealersHand) + ")";
        this.output.println(formatdealersHand);
    }
}
