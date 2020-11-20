package com.lingyi.memcachedclient.common;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressFrom {
    private final Stage dialogStage;

    public ProgressFrom(Stage stage) {
        dialogStage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        // 窗口父子关系
        dialogStage.initOwner(stage);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        // progress bar
        Label label = new Label("连接中, 请稍后...");
        label.setTextFill(Color.BLUE);
        progressIndicator.setProgress(-1F);
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setBackground(Background.EMPTY);
        vBox.getChildren().addAll(progressIndicator, label);
        Scene scene = new Scene(vBox);
        scene.setFill(null);
        dialogStage.setScene(scene);
    }

    public void activateProgressBar() {
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void cancelProgressBar() {
        dialogStage.close();
    }
}
