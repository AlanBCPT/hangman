package org.alanbldn.hangman.game;

public record Player(String name, boolean isComputer) {

    public static final Player COMPUTER = new Player("Computer", true);

    public static Player human(String name) {
        return new Player(name, false);
    }
}
