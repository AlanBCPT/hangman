package org.alanbldn.hangman.round;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SecretWord {

    private final String text;
    private final Set<Character> revealedLetters = new HashSet<>();

    public SecretWord(String text) {
        this.text = text.trim().toUpperCase();
    }

    public boolean contains(char letter) {
        return text.indexOf(letter) >= 0;
    }

    void reveal(char letter) {
        revealedLetters.add(letter);
    }

    public boolean isFullyRevealed() {
        return text.chars().allMatch(c -> c == ' ' || revealedLetters.contains((char) c));
    }

    public String maskedDisplay() {
        StringBuilder display = new StringBuilder();
        for (char c : rawMask().toCharArray()) {
            if (c == ' ') {
                display.append("   ");
            } else {
                display.append(c).append(' ');
            }
        }
        return display.toString().stripTrailing();
    }

    public List<String> maskedWords() {
        return List.of(rawMask().split(" "));
    }

    public String revealedText() {
        return text;
    }

    private String rawMask() {
        StringBuilder masked = new StringBuilder();
        for (char c : text.toCharArray()) {
            masked.append(c == ' ' ? ' ' : (revealedLetters.contains(c) ? c : '_'));
        }
        return masked.toString();
    }
}
