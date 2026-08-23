package org.alanbldn.hangman.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.alanbldn.hangman.ai.ComputerGuesser;
import org.alanbldn.hangman.game.GameMode;
import org.alanbldn.hangman.game.GameSetup;
import org.alanbldn.hangman.game.Player;
import org.alanbldn.hangman.game.WordSource;
import org.alanbldn.hangman.round.GuessResult;
import org.alanbldn.hangman.round.RoundOutcome;
import org.alanbldn.hangman.round.RoundState;
import org.alanbldn.hangman.round.SecretWord;
import org.alanbldn.hangman.theme.HangmanTheme;
import org.alanbldn.hangman.words.DictionaryWordRepository;
import org.alanbldn.hangman.words.WordRepository;

public final class GameFlowController {

    private static final int SCENE_WIDTH = 760;
    private static final int SCENE_HEIGHT = 600;
    private static final int LETTERS_PER_ROW = 13;
    private static final int THEME_PREVIEW_HEIGHT = 120;
    private static final int STAGE_IMAGE_HEIGHT = 260;
    private static final String DEFAULT_PLAYER_ONE_NAME = "Player 1";
    private static final String DEFAULT_PLAYER_TWO_NAME = "Player 2";

    private final Stage stage;
    private final ViewFactory viewFactory = new ViewFactory();
    private final WordRepository wordRepository = new DictionaryWordRepository();
    private final ComputerGuesser computerGuesser = new ComputerGuesser(wordRepository);
    private GameSetup setup;
    private String lastPlayerOneName = DEFAULT_PLAYER_ONE_NAME;
    private String lastPlayerTwoName = DEFAULT_PLAYER_TWO_NAME;

    public GameFlowController(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        setup = new GameSetup();
        showGameModeStep();
    }

    private void showGameModeStep() {
        Label title = viewFactory.createStepTitle("Who do you want to play against?");

        Button vsComputer = viewFactory.createChoiceButton("The Computer");
        vsComputer.setOnAction(e -> {
            setup.mode(GameMode.COMPUTER_OPPONENT);
            showPlayerNamesStep();
        });

        Button vsPlayer = viewFactory.createChoiceButton("Another Player");
        vsPlayer.setOnAction(e -> {
            setup.mode(GameMode.TWO_PLAYERS);
            showPlayerNamesStep();
        });

        VBox root = new VBox(20, title, vsComputer, vsPlayer);
        showStep(root, "Choose Opponent");
    }

    private void showPlayerNamesStep() {
        TextField playerOneField = new TextField(lastPlayerOneName);
        playerOneField.getStyleClass().add("name-field");

        VBox root = new VBox(20);
        root.getChildren().addAll(viewFactory.createFieldLabel("Enter Player 1's name:"), playerOneField);

        TextField playerTwoField = new TextField();
        playerTwoField.getStyleClass().add("name-field");
        if (setup.mode() == GameMode.TWO_PLAYERS) {
            playerTwoField.setText(lastPlayerTwoName);
            root.getChildren().addAll(viewFactory.createFieldLabel("Enter Player 2's name:"), playerTwoField);
        }

        Button next = viewFactory.createPrimaryButton("Next");
        next.setOnAction(e -> {
            lastPlayerOneName = defaultIfBlank(playerOneField.getText(), DEFAULT_PLAYER_ONE_NAME);
            setup.playerOne(Player.human(lastPlayerOneName));
            if (setup.mode() == GameMode.TWO_PLAYERS) {
                lastPlayerTwoName = defaultIfBlank(playerTwoField.getText(), DEFAULT_PLAYER_TWO_NAME);
                setup.playerTwo(Player.human(lastPlayerTwoName));
            } else {
                setup.playerTwo(Player.COMPUTER);
            }
            showThemeStep();
        });
        root.getChildren().add(next);

        showStep(root, "Player Names");
    }

    private void showThemeStep() {
        HBox options = new HBox(30);
        options.setAlignment(Pos.CENTER);

        for (HangmanTheme theme : HangmanTheme.values()) {
            ImageView preview = viewFactory.createImageView(
                    viewFactory.loadImage(theme.headPreviewPath()), THEME_PREVIEW_HEIGHT);

            Button select = viewFactory.createChoiceButton(theme.displayName());
            select.setOnAction(e -> {
                setup.theme(theme);
                showWordSourceStep();
            });

            VBox card = new VBox(10, preview, select);
            card.setAlignment(Pos.CENTER);
            card.getStyleClass().add("theme-card");
            options.getChildren().add(card);
        }

        VBox root = new VBox(20, viewFactory.createStepTitle("Choose a hangman style:"), options);
        showStep(root, "Choose Style");
    }

    private void showWordSourceStep() {
        Label question = viewFactory.createStepTitle("Who will choose the secret word?");

        Button playerOneChooses = viewFactory.createChoiceButton(setup.playerOne().name());
        playerOneChooses.setOnAction(e -> {
            setup.wordSource(WordSource.PLAYER_ONE);
            afterWordSourceChosen();
        });

        Button playerTwoChooses = viewFactory.createChoiceButton(setup.playerTwo().name());
        playerTwoChooses.setOnAction(e -> {
            setup.wordSource(WordSource.PLAYER_TWO);
            afterWordSourceChosen();
        });

        VBox root = new VBox(20, question, playerOneChooses, playerTwoChooses);
        showStep(root, "Choose Word Giver");
    }

    private void afterWordSourceChosen() {
        if (setup.wordGiver().isComputer()) {
            setup.secretWord(wordRepository.randomWord());
            showGameplayStep();
        } else {
            showSecretWordEntryStep();
        }
    }

    private void showSecretWordEntryStep() {
        Label prompt = viewFactory.createStepTitle(setup.wordGiver().name() + ", enter the secret word or phrase:");
        PasswordField wordField = new PasswordField();
        wordField.getStyleClass().add("secret-field");

        Button next = viewFactory.createPrimaryButton("Next");
        next.setDisable(true);
        wordField.textProperty().addListener((observable, oldValue, newValue) -> next.setDisable(newValue.isBlank()));

        next.setOnAction(e -> {
            setup.secretWord(wordField.getText());
            if (setup.guesser().isComputer()) {
                showGameplayStep();
            } else {
                showHandoffStep();
            }
        });

        VBox root = new VBox(20, prompt, wordField, next);
        showStep(root, "Secret Word");
    }

    private void showHandoffStep() {
        Label message = viewFactory.createStepTitle("Pass the device to " + setup.guesser().name() + ".");
        Button start = viewFactory.createPrimaryButton("Start Guessing");
        start.setOnAction(e -> showGameplayStep());

        VBox root = new VBox(20, message, start);
        showStep(root, "Get Ready");
    }

    private void showGameplayStep() {
        RoundState roundState = new RoundState(new SecretWord(setup.secretWord()));

        ImageView stageImage = viewFactory.createImageView(null, STAGE_IMAGE_HEIGHT);
        Label wordLabel = new Label();
        wordLabel.getStyleClass().add("word-label");
        Label statusLabel = new Label(setup.guesser().name() + " is guessing");
        statusLabel.getStyleClass().add("status-label");

        Runnable refreshBoard = () -> updateBoard(stageImage, wordLabel, roundState);
        refreshBoard.run();

        Map<Character, Button> letterButtons = new LinkedHashMap<>();
        boolean guesserIsComputer = setup.guesser().isComputer();
        Consumer<Character> applyGuess = letter -> handleGuess(letter, roundState, letterButtons, refreshBoard);

        GridPane alphabet = buildAlphabetGrid(letterButtons, applyGuess, guesserIsComputer);
        VBox root = new VBox(12, statusLabel, stageImage, wordLabel, alphabet);

        if (guesserIsComputer) {
            root.getChildren().add(buildComputerGuessButton(roundState, applyGuess));
        }

        showStep(root, "Guess the Word");
    }

    private void updateBoard(ImageView stageImage, Label wordLabel, RoundState roundState) {
        stageImage.setImage(viewFactory.loadImage(setup.theme().stageImagePath(roundState.wrongGuessCount())));
        wordLabel.setText(roundState.secretWord().maskedDisplay());
    }

    private void handleGuess(char letter, RoundState roundState, Map<Character, Button> letterButtons,
                              Runnable refreshBoard) {
        GuessResult result = roundState.guess(letter);
        Button button = letterButtons.get(letter);
        button.setDisable(true);
        button.getStyleClass().add(result == GuessResult.CORRECT ? "correct-letter" : "incorrect-letter");
        refreshBoard.run();

        RoundOutcome outcome = roundState.outcome();
        if (outcome != RoundOutcome.IN_PROGRESS) {
            showGameOverStep(outcome, roundState);
        }
    }

    private Button buildComputerGuessButton(RoundState roundState, Consumer<Character> applyGuess) {
        Button computerGuessButton = viewFactory.createPrimaryButton("Let the Computer Guess");
        computerGuessButton.setOnAction(e -> {
            char letter = computerGuesser.nextGuess(roundState);
            applyGuess.accept(letter);
            computerGuessButton.setDisable(roundState.outcome() != RoundOutcome.IN_PROGRESS);
        });
        return computerGuessButton;
    }

    private GridPane buildAlphabetGrid(Map<Character, Button> letterButtons, Consumer<Character> applyGuess,
                                        boolean guesserIsComputer) {
        GridPane alphabet = new GridPane();
        alphabet.getStyleClass().add("alphabet-grid");
        alphabet.setHgap(8);
        alphabet.setVgap(8);
        alphabet.setAlignment(Pos.CENTER);

        int column = 0;
        int row = 0;
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            char currentLetter = letter;
            Button button = new Button(String.valueOf(letter));
            button.getStyleClass().add("letter-button");
            button.setDisable(guesserIsComputer);
            button.setOnAction(e -> applyGuess.accept(currentLetter));
            letterButtons.put(letter, button);
            alphabet.add(button, column, row);
            column++;
            if (column == LETTERS_PER_ROW) {
                column = 0;
                row++;
            }
        }
        return alphabet;
    }

    private void showGameOverStep(RoundOutcome outcome, RoundState roundState) {
        Player winner = outcome == RoundOutcome.WORD_GUESSED ? setup.guesser() : setup.wordGiver();
        Label resultLabel = viewFactory.createStepTitle(winner.name() + " wins! The word was \""
                + roundState.secretWord().revealedText() + "\".");
        resultLabel.getStyleClass().add("result-label");

        ImageView finalImage = viewFactory.createImageView(
                viewFactory.loadImage(setup.theme().stageImagePath(roundState.wrongGuessCount())),
                STAGE_IMAGE_HEIGHT);

        Button playAgain = viewFactory.createPrimaryButton("Play Again");
        playAgain.setOnAction(e -> start());

        VBox root = new VBox(20, finalImage, resultLabel, playAgain);
        showStep(root, "Game Over");
    }

    private void showStep(VBox root, String title) {
        root.setAlignment(Pos.CENTER);
        setScene(root, title);
    }

    private void setScene(Parent root, String title) {
        stage.setTitle("Hangman - " + title);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/css/hangman.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private static String defaultIfBlank(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }
}
