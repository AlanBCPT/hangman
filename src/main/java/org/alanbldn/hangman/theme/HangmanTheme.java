package org.alanbldn.hangman.theme;

public enum HangmanTheme {

    TRADITIONAL("Traditional", "images/hangman"),
    CARTOON("Cartoon", "images/hangman_cartoon"),
    PIRATE("Pirate", "images/hangman_pirate");

    private final String displayName;
    private final String resourceFolder;

    HangmanTheme(String displayName, String resourceFolder) {
        this.displayName = displayName;
        this.resourceFolder = resourceFolder;
    }

    public String displayName() {
        return displayName;
    }

    public String headPreviewPath() {
        return "/" + resourceFolder + "/head.png";
    }

    public String stageImagePath(int wrongGuessCount) {
        return "/" + resourceFolder + "/stage_" + wrongGuessCount + ".png";
    }
}
