package com.lingyi.memcachedclient.common;

import com.lingyi.memcachedclient.pojo.MemcachedConnInfo;
import javafx.scene.control.TreeItem;
import net.rubyeye.xmemcached.MemcachedClient;

import java.util.HashMap;
import java.util.Map;

public class NodeUtil {

    public static TreeItem<String> treeItem;

    public static Map<TreeItem<String>, MemcachedConnInfo> map = new HashMap<>();

    public static Map<MemcachedConnInfo, MemcachedClient> map1 = new HashMap<>();
}
