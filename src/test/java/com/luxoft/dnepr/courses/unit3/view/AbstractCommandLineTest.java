package com.luxoft.dnepr.courses.unit3.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;

/**
 * Вспомагательный класс, который поможет тестировать консольные приложения.
 * Пока не нужно сильно углубляться в его работу. Достаточно знать что он эмулирует консоль для юнит тестов.
 */
abstract class AbstractCommandLineTest {
    private volatile PrintStream output;
    private volatile InputStream input;
    private volatile PipedOutputStream pipe;
    private volatile ByteArrayOutputStream writer;
    private volatile RunnableGame game;

    private void resetStreams() {
        try {
            writer = new ByteArrayOutputStream();
            output = new PrintStream(writer);
            pipe = new PipedOutputStream();
            input = new PipedInputStream(pipe);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Проверяет что наше приложение записало на выход именно эту строку.
     * Пробелы и переводы строк в начале и конце вывода будут проигнорированы.
     *
     * @param expected
     */
    protected void assertWritten(String expected) {
        Assert.assertEquals(expected, getWritten());
    }

    /**
     * @return строку, которую наша программа вывела в консоль.
     */
    protected String getWritten() {
        try {
            return writer.toString("UTF8").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
            return "";// never happens
        }
    }

    /**
     * Очищает весь вывод программы. Удобно для тестов.
     */
    protected void clearWritten() {
        writer.reset();
    }

    /**
     * Запускает игру блекджек в отдельном потоке.
     * Гарантируется что игра не будет препятствовать завершению теста и джава машины вцелом.
     */
    protected synchronized void startGame() {
        if (game != null) {
            throw new IllegalStateException("Game is already started!");
        }
        resetStreams();
        game = new RunnableGame(input, output);
        game.start();
    }

    /**
     * Делает попытку остановить игру. Не гарантируется что успешно.
     */
    protected synchronized void forceExitGame() {
        try {
            input.close();
        } catch (IOException ex) {
            // ignoring
        }
        game = null;
    }

    /**
     * Ждет, пока игра не начнет запрашивать данные с консоли (но не более 5 секунд).
     */
    protected void await() {
        synchronized (input) {
            try {
                input.wait(5000);
            } catch (InterruptedException ex) {
                return;
            }
        }
    }

    /**
     * Эмулирует пользовательский ввод в консоли.
     * Другими словами вызов этого метода эквивалентен сообщению с клавиатуры.
     * Не забывайте добавлять перевод строки после вывода ('\n')
     *
     * @param message
     */
    protected void respond(final String message) {
        synchronized (input) {
            try {
                pipe.write(message.getBytes());
                pipe.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class RunnableGame extends Thread {

        private final CommandLineInterface cli;

        RunnableGame(InputStream input, PrintStream output) {
            setDaemon(true);
            cli = new CommandLineInterface(output, input);
        }

        @Override
        public void run() {
            Thread.yield();
            cli.play();
        }
    }
}
