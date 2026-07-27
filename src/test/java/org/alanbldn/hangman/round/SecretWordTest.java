package org.alanbldn.hangman.round;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecretWordTest {

    @Test
    void masksEveryLetterUntilRevealed() {
        SecretWord secretWord = new SecretWord("cat");

        assertEquals("_ _ _", secretWord.maskedDisplay());
    }

    @Test
    void revealsAllOccurrencesOfAGuessedLetter() {
        SecretWord secretWord = new SecretWord("banana");

        secretWord.reveal('A');

        assertEquals("_ A _ A _ A", secretWord.maskedDisplay());
    }

    @Test
    void preservesSpacesBetweenWordsInAPhrase() {
        SecretWord secretWord = new SecretWord("swimming pool");

        assertEquals("_ _ _ _ _ _ _ _    _ _ _ _", secretWord.maskedDisplay());
    }

    @Test
    void isFullyRevealedOnlyAfterEveryLetterIsGuessed() {
        SecretWord secretWord = new SecretWord("cat");

        secretWord.reveal('C');
        secretWord.reveal('A');
        assertFalse(secretWord.isFullyRevealed());

        secretWord.reveal('T');
        assertTrue(secretWord.isFullyRevealed());
    }

    @Test
    void splitsMaskedWordsOnSpaces() {
        SecretWord secretWord = new SecretWord("swimming pool");

        assertEquals(2, secretWord.maskedWords().size());
        assertEquals("________", secretWord.maskedWords().get(0));
        assertEquals("____", secretWord.maskedWords().get(1));
    }

    @Test
    void maskedWordsIsASingleElementWhenTextHasNoSpaces() {
        SecretWord secretWord = new SecretWord("cat");

        assertEquals(1, secretWord.maskedWords().size());
        assertEquals("___", secretWord.maskedWords().get(0));
    }

    @Test
    void revealedTextIsTheTrimmedUppercasedOriginalWord() {
        SecretWord secretWord = new SecretWord("  swimming pool  ");

        assertEquals("SWIMMING POOL", secretWord.revealedText());
    }
}
