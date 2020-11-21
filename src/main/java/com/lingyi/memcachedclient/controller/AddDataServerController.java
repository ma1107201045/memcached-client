package com.lingyi.memcachedclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddDataServerController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField key;
    @FXML
    private TextField value;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onOk() {

    }

    public void onCancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
