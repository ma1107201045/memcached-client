package com.lingyi.memcachedclient.controller;

import com.lingyi.memcachedclient.common.NodeUtil;
import com.lingyi.memcachedclient.pojo.MemcachedConnInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.net.URL;
import java.util.ResourceBundle;

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
        MemcachedConnInfo memcachedConnInfo = new MemcachedConnInfo(name.getText(), host.getText(), Integer.parseInt(port.getText()));
        MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(AddrUtil.getAddresses(memcachedConnInfo.getHost() + ":" + memcachedConnInfo.getPort()));
        try {
            MemcachedClient memcachedClient = memcachedClientBuilder.build();
            if (memcachedClient.getVersions().isEmpty())
                throw new RuntimeException("");
            TreeItem<String> rootItem = NodeUtil.treeItem;
            TreeItem<String> treeItem = new TreeItem<>(name.getText());
            ImageView imageView = new ImageView("/img/icon.png");
            imageView.setFitWidth(16.0);
            imageView.setFitHeight(16.0);
            treeItem.setGraphic(imageView);
            rootItem.getChildren().add(treeItem);
            rootItem.setExpanded(true);
            NodeUtil.map.put(treeItem, memcachedConnInfo);
            NodeUtil.map1.put(memcachedConnInfo, memcachedClient);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "连接成功", ButtonType.OK);
            alert.show();
            onCancel();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "连接失败，具体原因：" + e.getMessage(), ButtonType.OK);
            alert.show();
        }

    }

    public void onCancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
