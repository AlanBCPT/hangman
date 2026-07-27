package org.alanbldn.hangman.round;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundStateTest {

    @Test
    void correctGuessDoesNotIncreaseWrongGuessCount() {
        RoundState round = new RoundState(new SecretWord("cat"));

        assertEquals(GuessResult.CORRECT, round.guess('c'));
        assertEquals(0, round.wrongGuessCount());
    }

    @Test
    void incorrectGuessIncreasesWrongGuessCount() {
        RoundState round = new RoundState(new SecretWord("cat"));

        assertEquals(GuessResult.INCORRECT, round.guess('z'));
        assertEquals(1, round.wrongGuessCount());
    }

    @Test
    void repeatingALetterIsReportedAsAlreadyGuessed() {
        RoundState round = new RoundState(new SecretWord("cat"));

        round.guess('c');

        assertEquals(GuessResult.ALREADY_GUESSED, round.guess('c'));
        assertEquals(0, round.wrongGuessCount());
    }

    @Test
    void outcomeIsWordGuessedWhenEveryLetterIsFound() {
        RoundState round = new RoundState(new SecretWord("cat"));

        round.guess('c');
        round.guess('a');
        round.guess('t');

        assertEquals(RoundOutcome.WORD_GUESSED, round.outcome());
    }

    @Test
    void outcomeIsHangmanCompleteAfterSevenWrongGuesses() {
        RoundState round = new RoundState(new SecretWord("cat"));

        for (char letter : "bdefgh".toCharArray()) {
            round.guess(letter);
        }
        assertEquals(RoundOutcome.IN_PROGRESS, round.outcome());

        round.guess('i');

        assertEquals(RoundOutcome.HANGMAN_COMPLETE, round.outcome());
        assertEquals(7, round.wrongGuessCount());
    }
}
