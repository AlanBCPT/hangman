package org.alanbldn.hangman;

import javafx.application.Application;
import javafx.stage.Stage;
import org.alanbldn.hangman.ui.GameFlowController;

public class HangmanApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new GameFlowController(primaryStage).start();
    }
}
