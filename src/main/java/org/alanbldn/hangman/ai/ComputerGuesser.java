package org.alanbldn.hangman.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.alanbldn.hangman.round.RoundState;
import org.alanbldn.hangman.words.WordRepository;

public final class ComputerGuesser {

    private static final String ENGLISH_FREQUENCY_ORDER = "ETAOINSHRDLUCMFWYGPBVKJXQZ";

    private final WordRepository wordRepository;

    public ComputerGuesser(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public char nextGuess(RoundState roundState) {
        Set<Character> guessed = roundState.guessedLetters();
        Character bestCandidate = mostLikelyLetterFromCandidates(roundState, guessed);
        return bestCandidate != null ? bestCandidate : fallbackFrequencyGuess(guessed);
    }

    private Character mostLikelyLetterFromCandidates(RoundState roundState, Set<Character> guessed) {
        Map<Character, Integer> letterCounts = new HashMap<>();
        for (String maskedWord : roundState.secretWord().maskedWords()) {
            for (String candidate : wordRepository.allWords()) {
                if (!matches(candidate, maskedWord, guessed)) {
                    continue;
                }
                for (char letter : candidate.toCharArray()) {
                    if (!guessed.contains(letter)) {
                        letterCounts.merge(letter, 1, Integer::sum);
                    }
                }
            }
        }
        return letterCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private boolean matches(String candidate, String maskedWord, Set<Character> guessed) {
        if (candidate.length() != maskedWord.length()) {
            return false;
        }
        for (int i = 0; i < maskedWord.length(); i++) {
            char maskChar = maskedWord.charAt(i);
            char candidateChar = candidate.charAt(i);
            if (maskChar != '_' && maskChar != candidateChar) {
                return false;
            }
            if (maskChar == '_' && guessed.contains(candidateChar)) {
                return false;
            }
        }
        return true;
    }

    private char fallbackFrequencyGuess(Set<Character> guessed) {
        return ENGLISH_FREQUENCY_ORDER.chars()
                .mapToObj(c -> (char) c)
                .filter(letter -> !guessed.contains(letter))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No letters left to guess"));
    }
}
