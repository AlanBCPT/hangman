package org.alanbldn.hangman.game;

import org.alanbldn.hangman.theme.HangmanTheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameSetupTest {

    @Test
    void wordGiverIsPlayerOneWhenWordSourceIsPlayerOne() {
        GameSetup setup = new GameSetup();
        Player playerOne = Player.human("Alice");
        Player playerTwo = Player.human("Bob");
        setup.playerOne(playerOne);
        setup.playerTwo(playerTwo);
        setup.wordSource(WordSource.PLAYER_ONE);

        assertEquals(playerOne, setup.wordGiver());
        assertEquals(playerTwo, setup.guesser());
    }

    @Test
    void wordGiverIsPlayerTwoWhenWordSourceIsPlayerTwo() {
        GameSetup setup = new GameSetup();
        Player playerOne = Player.human("Alice");
        Player playerTwo = Player.human("Bob");
        setup.playerOne(playerOne);
        setup.playerTwo(playerTwo);
        setup.wordSource(WordSource.PLAYER_TWO);

        assertEquals(playerTwo, setup.wordGiver());
        assertEquals(playerOne, setup.guesser());
    }

    @Test
    void storesModeThemeAndSecretWord() {
        GameSetup setup = new GameSetup();
        setup.mode(GameMode.COMPUTER_OPPONENT);
        setup.theme(HangmanTheme.PIRATE);
        setup.secretWord("parrot");

        assertEquals(GameMode.COMPUTER_OPPONENT, setup.mode());
        assertEquals(HangmanTheme.PIRATE, setup.theme());
        assertEquals("parrot", setup.secretWord());
    }

    @Test
    void fieldsAreNullUntilSet() {
        GameSetup setup = new GameSetup();

        assertNull(setup.mode());
        assertNull(setup.playerOne());
        assertNull(setup.playerTwo());
        assertNull(setup.theme());
        assertNull(setup.wordSource());
        assertNull(setup.secretWord());
    }
}
