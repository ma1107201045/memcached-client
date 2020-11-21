package com.lingyi.memcachedclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MCApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        primaryStage.setTitle("Memcached client");
        primaryStage.setWidth(1024.0);
        primaryStage.setHeight(768.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
