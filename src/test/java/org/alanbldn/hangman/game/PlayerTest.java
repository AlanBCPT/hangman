package org.alanbldn.hangman.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    @Test
    void computerConstantIsMarkedAsComputer() {
        assertEquals("Computer", Player.COMPUTER.name());
        assertTrue(Player.COMPUTER.isComputer());
    }

    @Test
    void humanFactoryCreatesNonComputerPlayerWithGivenName() {
        Player player = Player.human("Alice");

        assertEquals("Alice", player.name());
        assertFalse(player.isComputer());
    }
}
