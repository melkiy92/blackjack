package com.luxoft.dnepr.courses.unit3.view;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandLineInterfaceTest extends AbstractCommandLineTest {

    private static final String CARD_PATTERN = "([123456789JKQA]|(10))";
    private static final String TOTAL_PATTERN = "\\(total \\d+\\)";

    @After
    public void destroy() {
        // этот метод выполнится после любого теста и гарантирует что игра завершилась успешно.
        forceExitGame();
    }

    @Test
    public void testHelp() {
        // начинаем игру
        startGame();

        // дем пока программа не попросит ввода
        await();

        // очищаем то что она успела вывести
        clearWritten();

        // даем команду help
        respond("help\n");

        // ждем результата
        await();

        // проверяем что хелп напечатался
        assertWritten("Usage: \n" +
                "\thelp - prints this message\n" +
                "\thit - requests one more card\n" +
                "\tstand - I'm done - lets finish\n" +
                "\texit - exits game");
    }

    @Test
    public void testHit() {
        startGame();
        await();
        clearWritten();
        respond("hit\n");
        await();

        String[] lines = getWritten().split("\n");
        assertTrue(lines.length == 2 || lines.length == 4);

        assertTrue(lines[0].trim().matches("^" + CARD_PATTERN + " " +
                CARD_PATTERN + " " + CARD_PATTERN + " " + TOTAL_PATTERN + "$"));

        assertTrue(lines[1].trim().matches("^" + CARD_PATTERN + " " + TOTAL_PATTERN + "$"));

        if (lines.length == 2) {
            return;
        }
        //noinspection Since15
        assertTrue(lines[2].trim().isEmpty());

        assertEquals("Sorry, today is not your day. You loose.", lines[3]);
    }

    private boolean isValidResponse(String response) {
        return response.equals("Sorry, today is not your day. You loose.") ||
                response.equals("Congrats! You win!") ||
                response.equals("Push. Everybody has equal amount of points.");
    }

    @Test
    public void testStand() {
        startGame();
        await();
        clearWritten();
        respond("stand\n");
        await();

        String[] lines = getWritten().split("\n");
        assertEquals(6, lines.length);

        assertEquals("Dealer turn:", lines[0].trim());

        //noinspection Since15
        assertTrue(lines[1].trim().isEmpty());

        //// *
        System.out.println("Checkin expression: \"" + lines[2].trim() + "\".matches(\"^" + CARD_PATTERN + " " +
                CARD_PATTERN + " " + TOTAL_PATTERN + "$\")");
        assertTrue(lines[2].trim().matches("^" + CARD_PATTERN + " " +
                CARD_PATTERN + " " + TOTAL_PATTERN + "$"));
        ////

        assertTrue(lines[2].trim().matches("^" + CARD_PATTERN + " " +
                CARD_PATTERN + " " + TOTAL_PATTERN + "$"));


        System.out.println("Checkin expression: \"" + lines[3].trim() + "\".matches(^(" + CARD_PATTERN + " )+" + TOTAL_PATTERN + "$\")");
        assertTrue(lines[3].trim().matches("^(" + CARD_PATTERN + " )+" + TOTAL_PATTERN + "$"));  ///


        //noinspection Since15
        assertTrue(lines[4].trim().isEmpty());

        assertTrue(isValidResponse(lines[5].trim()));
    }
}
