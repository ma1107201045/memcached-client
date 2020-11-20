package com.lingyi.memcachedclient.controller;

import com.lingyi.memcachedclient.common.NodeUtil;
import com.lingyi.memcachedclient.common.ProgressFrom;
import com.lingyi.memcachedclient.pojo.MemcachedConnInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddServerController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField name;

    @FXML
    private TextField host;

    @FXML
    private TextField port;

    @Override

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onOk() {
        TreeItem<String> rootItem = NodeUtil.treeItem;
        TreeItem<String> treeItem = new TreeItem<>(name.getText());
        ImageView imageView = new ImageView("/img/icon.png");
        imageView.setFitWidth(16.0);
        imageView.setFitHeight(16.0);
        treeItem.setGraphic(imageView);
        rootItem.getChildren().add(treeItem);
        rootItem.setExpanded(true);
        MemcachedConnInfo memcachedConnInfo = new MemcachedConnInfo(name.getText(), host.getText(), Integer.parseInt(port.getText()));
        NodeUtil.map.put(treeItem, memcachedConnInfo);
        MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(AddrUtil.getAddresses(memcachedConnInfo.getHost() + ":" + memcachedConnInfo.getPort()));
        try {
            MemcachedClient memcachedClient = memcachedClientBuilder.build();
            NodeUtil.map1.put(memcachedConnInfo, memcachedClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        onCancel();
    }

    public void onCancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
