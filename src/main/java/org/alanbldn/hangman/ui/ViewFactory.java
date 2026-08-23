package org.alanbldn.hangman.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

final class ViewFactory {

    Label createStepTitle(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("step-title");
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMaxWidth(620);
        return label;
    }

    Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");
        return label;
    }

    Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("primary-button");
        return button;
    }

    Button createChoiceButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("choice-button");
        return button;
    }

    ImageView createImageView(Image image, double fitHeight) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(fitHeight);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    Image loadImage(String path) {
        return new Image(getClass().getResourceAsStream(path));
    }
}
