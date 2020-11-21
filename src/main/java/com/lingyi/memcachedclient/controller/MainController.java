package com.lingyi.memcachedclient.controller;

import com.lingyi.memcachedclient.common.NodeUtil;
import com.lingyi.memcachedclient.pojo.Keys;
import com.lingyi.memcachedclient.pojo.MemcachedConnInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

public class MainController implements Initializable {
    @FXML
    private TreeView<String> treeView;

    private TreeItem<String> rootTreeItem;

    private ContextMenu rootContextMenu;

    private ContextMenu serverContextMenu;
    @FXML
    private Tab explorer;
    @FXML
    private TableView<Keys> tableView;
    @FXML
    private TableColumn<Keys, SimpleStringProperty> key;
    @FXML
    private TableColumn<Keys, SimpleStringProperty> value;

    private ObservableList<Keys> data;

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        createRootTreeItem();
        NodeUtil.treeItem = rootTreeItem;
        createServerContextMenu();
        createRootContextMenu();
        treeView.setRoot(rootTreeItem);
        treeView.setOnMouseClicked(mouseEvent -> {
            rootContextMenu.hide();
            serverContextMenu.hide();
        });
        treeView.setOnContextMenuRequested(contextMenuEvent -> {
            int selectIndex = treeView.getSelectionModel().getSelectedIndex();
            if (selectIndex == 1) {
                serverContextMenu.hide();
                rootContextMenu.hide();
                serverContextMenu.show(treeView, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            } else {
                rootContextMenu.hide();
                serverContextMenu.hide();
                rootContextMenu.show(treeView, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
        treeView.getSelectionModel().selectedItemProperty().addListener(observable -> {
            TreeItem<String> treeItem = treeView.getSelectionModel().getSelectedItem();
            MemcachedConnInfo memcachedConnInfo = NodeUtil.map.get(treeItem);

            if (memcachedConnInfo != null) {
                MemcachedClient memcachedClient = NodeUtil.map1.get(memcachedConnInfo);
                KeyIterator it;
                try {
                    it = memcachedClient.getKeyIterator(AddrUtil.getOneAddress(memcachedConnInfo.getHost() + ":" + memcachedConnInfo.getPort()));
                    List<Keys> keys = new ArrayList<>();
                    while (it.hasNext()) {
                        String key = it.next();
                        String value = memcachedClient.get(key);
                        keys.add(new Keys(key, value));
                    }
                    data = FXCollections.observableArrayList(keys);
                    tableView.setItems(data);
                } catch (MemcachedException | InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                }
            }

        });
        setIcon();
        key.setCellValueFactory(new PropertyValueFactory<>("key"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
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
        menuItem5.setOnAction(actionEvent -> treeView.refresh());
        rootContextMenu.getItems().addAll(menuItem1, new SeparatorMenuItem(), menuItem2, menuItem3, menuItem4, new SeparatorMenuItem(), menuItem5);
    }

    public void createServerContextMenu() {
        serverContextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Update");
        MenuItem menuItem2 = new MenuItem("Remove");
        MenuItem menuItem3 = new MenuItem("Properties");
        MenuItem menuItem4 = new MenuItem("Find");
        MenuItem menuItem5 = new MenuItem("Find forward");
        MenuItem menuItem6 = new MenuItem("Find backward");
        MenuItem menuItem7 = new MenuItem("Refresh");
        menuItem7.setOnAction(actionEvent -> treeView.refresh());
        serverContextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, new SeparatorMenuItem(), menuItem4, menuItem5, menuItem6, new SeparatorMenuItem(), menuItem7);

    }

    public void setIcon() {
        ImageView imageView = new ImageView("/img/icon.png");
        imageView.setFitWidth(16.0);
        imageView.setFitHeight(16.0);
        explorer.setGraphic(imageView);
    }
}
