package org.alanbldn.hangman.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordSourceTest {

    @Test
    void hasPlayerOneAndPlayerTwoValues() {
        assertArrayEquals(new WordSource[] {WordSource.PLAYER_ONE, WordSource.PLAYER_TWO}, WordSource.values());
        assertEquals(WordSource.PLAYER_ONE, WordSource.valueOf("PLAYER_ONE"));
    }
}
