package org.alanbldn.hangman.words;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class DictionaryWordRepository implements WordRepository {

    private static final String DICTIONARY_PATH = "/dictionary/the_wordlist.txt";

    private final List<String> words;
    private final Random random = new Random();

    public DictionaryWordRepository() {
        this.words = List.copyOf(loadWords());
    }

    @Override
    public String randomWord() {
        return words.get(random.nextInt(words.size()));
    }

    @Override
    public List<String> allWords() {
        return words;
    }

    private static List<String> loadWords() {
        try (InputStream stream = DictionaryWordRepository.class.getResourceAsStream(DICTIONARY_PATH)) {
            if (stream == null) {
                throw new IllegalStateException("Word list not found on classpath: " + DICTIONARY_PATH);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                return reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
