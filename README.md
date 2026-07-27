# Hangman

A desktop Hangman game built with JavaFX. Play against another person or against
the computer, choose from three visual themes, and pick who chooses the secret
word.

## Rules

- The secret word (or phrase) is shown as a blank for each letter, with a gap
  between blanks for each word in a phrase.
- Guess a letter: a correct guess reveals every occurrence of that letter; an
  incorrect guess adds one part to the gallows drawing.
- There are 7 wrong guesses available (rope, head, body, two arms, two legs)
  before the drawing is complete.
- The guesser wins by revealing every letter before the drawing is finished;
  otherwise the word-giver wins.
- Choosing "Play Again" after a round restarts the wizard from the top, with
  the player name field(s) pre-filled with whatever was typed last time.

## Playing against the computer

When the computer is the word-giver, it picks a random word from the built-in
word list. When a human player sets the secret word instead, the computer
guesses back: it filters the word list against the letters already revealed
and guessed, and guesses whichever remaining letter appears most often across
the matching candidates, falling back to standard English letter frequency
when nothing matches.

## Requirements

- Java 21
- Maven (or the bundled `mvnw` / `mvnw.cmd` wrapper)

## Running the game

```
mvn javafx:run
```

## Running the tests

```
mvn test
```

Tests cover the game rules, setup, theming, word repository, and computer
guesser logic. A JaCoCo coverage gate enforces 90% line coverage on that
domain code (the `round`, `game`, `theme`, and `ai` packages); the `words`
package is gated at 80%, since two defensive I/O branches in
`DictionaryWordRepository` can't be exercised without a broken classpath. The
JavaFX UI package is excluded from the gate, as it's view/wiring code best
verified by playing the game rather than by unit tests.

## Project structure

```
org.alanbldn.hangman
├── round    Secret word, guesses, and round/outcome state
├── game     Game mode, players, and setup collected by the wizard
├── theme    Traditional / cartoon / pirate image themes
├── words    The word list and random word selection
├── ai       The computer's letter-guessing strategy
└── ui       The JavaFX screens that drive the game, in order:
             choose opponent → names → theme → word-giver → secret word →
             handoff → gameplay → result
```
