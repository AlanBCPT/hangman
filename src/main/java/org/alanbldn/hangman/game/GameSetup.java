package org.alanbldn.hangman.game;

import org.alanbldn.hangman.theme.HangmanTheme;

public final class GameSetup {

    private GameMode mode;
    private Player playerOne;
    private Player playerTwo;
    private HangmanTheme theme;
    private WordSource wordSource;
    private String secretWord;

    public GameMode mode() {
        return mode;
    }

    public void mode(GameMode mode) {
        this.mode = mode;
    }

    public Player playerOne() {
        return playerOne;
    }

    public void playerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player playerTwo() {
        return playerTwo;
    }

    public void playerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public HangmanTheme theme() {
        return theme;
    }

    public void theme(HangmanTheme theme) {
        this.theme = theme;
    }

    public WordSource wordSource() {
        return wordSource;
    }

    public void wordSource(WordSource wordSource) {
        this.wordSource = wordSource;
    }

    public String secretWord() {
        return secretWord;
    }

    public void secretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public Player wordGiver() {
        return wordSource == WordSource.PLAYER_ONE ? playerOne : playerTwo;
    }

    public Player guesser() {
        return wordSource == WordSource.PLAYER_ONE ? playerTwo : playerOne;
    }
}
