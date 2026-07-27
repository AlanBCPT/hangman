package org.alanbldn.hangman.theme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HangmanThemeTest {

    @Test
    void traditionalThemeUsesHangmanImageFolder() {
        assertEquals("Traditional", HangmanTheme.TRADITIONAL.displayName());
        assertEquals("/images/hangman/head.png", HangmanTheme.TRADITIONAL.headPreviewPath());
        assertEquals("/images/hangman/stage_3.png", HangmanTheme.TRADITIONAL.stageImagePath(3));
    }

    @Test
    void cartoonThemeUsesHangmanCartoonImageFolder() {
        assertEquals("Cartoon", HangmanTheme.CARTOON.displayName());
        assertEquals("/images/hangman_cartoon/head.png", HangmanTheme.CARTOON.headPreviewPath());
        assertEquals("/images/hangman_cartoon/stage_0.png", HangmanTheme.CARTOON.stageImagePath(0));
    }

    @Test
    void pirateThemeUsesHangmanPirateImageFolder() {
        assertEquals("Pirate", HangmanTheme.PIRATE.displayName());
        assertEquals("/images/hangman_pirate/head.png", HangmanTheme.PIRATE.headPreviewPath());
        assertEquals("/images/hangman_pirate/stage_7.png", HangmanTheme.PIRATE.stageImagePath(7));
    }
}
