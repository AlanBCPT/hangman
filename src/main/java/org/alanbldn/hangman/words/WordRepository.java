package org.alanbldn.hangman.words;

import java.util.List;

public interface WordRepository {

    String randomWord();

    List<String> allWords();
}
