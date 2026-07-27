package org.alanbldn.hangman.round;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RoundState {

    public static final int MAX_WRONG_GUESSES = 7;

    private final SecretWord secretWord;
    private final Set<Character> guessedLetters = new LinkedHashSet<>();
    private int wrongGuessCount = 0;

    public RoundState(SecretWord secretWord) {
        this.secretWord = secretWord;
    }

    public GuessResult guess(char letter) {
        char upper = Character.toUpperCase(letter);
        if (guessedLetters.contains(upper)) {
            return GuessResult.ALREADY_GUESSED;
        }
        guessedLetters.add(upper);
        if (secretWord.contains(upper)) {
            secretWord.reveal(upper);
            return GuessResult.CORRECT;
        }
        wrongGuessCount++;
        return GuessResult.INCORRECT;
    }

    public RoundOutcome outcome() {
        if (secretWord.isFullyRevealed()) {
            return RoundOutcome.WORD_GUESSED;
        }
        if (wrongGuessCount >= MAX_WRONG_GUESSES) {
            return RoundOutcome.HANGMAN_COMPLETE;
        }
        return RoundOutcome.IN_PROGRESS;
    }

    public SecretWord secretWord() {
        return secretWord;
    }

    public Set<Character> guessedLetters() {
        return Collections.unmodifiableSet(guessedLetters);
    }

    public int wrongGuessCount() {
        return wrongGuessCount;
    }
}
