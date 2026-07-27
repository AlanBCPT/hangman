package org.alanbldn.hangman.ai;

import java.util.List;

import org.alanbldn.hangman.round.RoundState;
import org.alanbldn.hangman.round.SecretWord;
import org.alanbldn.hangman.words.WordRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComputerGuesserTest {

    private static final WordRepository FIXED_VOCABULARY = new WordRepository() {
        @Override
        public String randomWord() {
            return "CAT";
        }

        @Override
        public List<String> allWords() {
            return List.of("CAT", "CAR", "CAN", "BAT", "BAR", "DOG");
        }
    };

    @Test
    void neverRepeatsAnAlreadyGuessedLetter() {
        ComputerGuesser guesser = new ComputerGuesser(FIXED_VOCABULARY);
        RoundState round = new RoundState(new SecretWord("cat"));
        round.guess('c');
        round.guess('z');

        char nextGuess = guesser.nextGuess(round);

        assertFalse(round.guessedLetters().contains(nextGuess));
    }

    @Test
    void favoursLettersFromWordsConsistentWithKnownPattern() {
        ComputerGuesser guesser = new ComputerGuesser(FIXED_VOCABULARY);
        RoundState round = new RoundState(new SecretWord("cat"));
        round.guess('c');

        char nextGuess = guesser.nextGuess(round);

        assertTrue("ABTNR".indexOf(nextGuess) >= 0);
    }

    @Test
    void eliminatesCandidatesContainingAnAlreadyGuessedWrongLetterInABlankPosition() {
        ComputerGuesser guesser = new ComputerGuesser(FIXED_VOCABULARY);
        RoundState round = new RoundState(new SecretWord("cat"));
        round.guess('c');
        round.guess('r');

        char nextGuess = guesser.nextGuess(round);

        assertEquals('A', nextGuess);
    }

    @Test
    void aggregatesLetterFrequenciesAcrossEachWordInAPhrase() {
        ComputerGuesser guesser = new ComputerGuesser(FIXED_VOCABULARY);
        RoundState round = new RoundState(new SecretWord("cat dog"));

        char nextGuess = guesser.nextGuess(round);

        assertEquals('A', nextGuess);
    }

    @Test
    void ignoresCandidatesWithADifferentLengthThanTheMaskedWord() {
        ComputerGuesser guesser = new ComputerGuesser(FIXED_VOCABULARY);
        RoundState round = new RoundState(new SecretWord("goat"));

        char nextGuess = guesser.nextGuess(round);

        assertEquals('E', nextGuess);
    }

    @Test
    void fallsBackToFrequencyOrderWhenNoDictionaryWordMatches() {
        WordRepository emptyVocabulary = new WordRepository() {
            @Override
            public String randomWord() {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<String> allWords() {
                return List.of();
            }
        };
        ComputerGuesser guesser = new ComputerGuesser(emptyVocabulary);
        RoundState round = new RoundState(new SecretWord("cat"));

        assertTrue(guesser.nextGuess(round) == 'E');
    }
}
