package org.alanbldn.hangman.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameModeTest {

    @Test
    void hasComputerOpponentAndTwoPlayersValues() {
        assertArrayEquals(new GameMode[] {GameMode.COMPUTER_OPPONENT, GameMode.TWO_PLAYERS}, GameMode.values());
        assertEquals(GameMode.COMPUTER_OPPONENT, GameMode.valueOf("COMPUTER_OPPONENT"));
    }
}
