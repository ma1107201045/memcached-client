package com.lingyi.memcachedclient.controller;

import com.lingyi.memcachedclient.common.NodeUtil;
import com.lingyi.memcachedclient.pojo.MemcachedConnInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rubyeye.xmemcached.MemcachedClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TreeView<String> treeView;

    private TreeItem<String> rootTreeItem;

    private ContextMenu rootContextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createRootTreeItem();
        NodeUtil.treeItem = rootTreeItem;
        createRootContextMenu();
        treeView.setRoot(rootTreeItem);
        treeView.setOnContextMenuRequested(contextMenuEvent -> {
            rootContextMenu.hide();
            rootContextMenu.show(treeView, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
        });
        treeView.getSelectionModel().selectedItemProperty().addListener(observable -> {
            TreeItem<String> treeItem = treeView.getSelectionModel().getSelectedItem();
            MemcachedConnInfo memcachedConnInfo = NodeUtil.map.get(treeItem);
            if (memcachedConnInfo != null) {
                MemcachedClient memcachedClient = NodeUtil.map1.get(memcachedConnInfo);
                System.out.println(memcachedClient);
            }
        });
    }

    public void createRootTreeItem() {
        rootTreeItem = new TreeItem<>("Memcached Servers");
        ImageView imageView = new ImageView("/img/icon.png");
        imageView.setFitWidth(16.0);
        imageView.setFitHeight(16.0);
        rootTreeItem.setGraphic(imageView);
    }

    public void createRootContextMenu() {
        rootContextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Add server");
        menuItem1.setOnAction(actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/add-server.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setWidth(350.0);
                stage.setHeight(270.0);
                stage.setResizable(false);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
                stage.setTitle("Add server");
                stage.initOwner(treeView.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        MenuItem menuItem2 = new MenuItem("Find");
        MenuItem menuItem3 = new MenuItem("Find forward");
        MenuItem menuItem4 = new MenuItem("Find backward");
        MenuItem menuItem5 = new MenuItem("Refresh");
        rootContextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5);
    }
}
