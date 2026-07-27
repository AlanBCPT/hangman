package org.alanbldn.hangman.words;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DictionaryWordRepositoryTest {

    @Test
    void loadsANonEmptyWordListFromTheClasspath() {
        DictionaryWordRepository repository = new DictionaryWordRepository();

        assertFalse(repository.allWords().isEmpty());
    }

    @Test
    void randomWordComesFromAllWords() {
        DictionaryWordRepository repository = new DictionaryWordRepository();
        List<String> allWords = repository.allWords();

        assertTrue(allWords.contains(repository.randomWord()));
    }

    @Test
    void everyWordIsUppercase() {
        DictionaryWordRepository repository = new DictionaryWordRepository();

        for (String word : repository.allWords()) {
            assertTrue(word.equals(word.toUpperCase()));
        }
    }
}
