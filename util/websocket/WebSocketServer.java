package com.haishu.platform.common;


import com.alibaba.fastjson.JSONObject;
import com.haishu.platform.app.api.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocketServer
 *
 * @author liuyun
 * @date 2021/01/21
 */

@ServerEndpoint(value = "/websocket")
@Component
@Slf4j
public class WebSocketServer {
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static Map<String, CopyOnWriteArraySet<WebSocketServer>> socketMap = new HashMap();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        String userId = this.session.getQueryString();
        if (userId != null && !"".equals(userId)) {
            if (socketMap.get(userId) == null) {
                CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
                webSocketSet.add(this);
                socketMap.put(userId, webSocketSet);
            } else {
                socketMap.get(userId).add(this);
            }

        }

        addOnlineCount(); // 在线数加1
        log.info("{}连接成功！", userId);
        HashMap<String, String> map = new HashMap<>();
        map.put("msg", "连接成功");
        sendMessage(JSONObject.toJSONString(map));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        String userId = this.session.getQueryString();
        CopyOnWriteArraySet<WebSocketServer> webSocketServers = socketMap.get(userId);
        webSocketServers.remove(this);
        subOnlineCount(); // 在线数减1
        log.info("{}连接关闭！当前在线人数为{}", userId, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        /*log.info("来自客户端的消息:" + message);
        // 群发消息
        for (WebSocketServer item : socketMap.get("")) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        try {

            this.session.getBasicRemote().sendText(message);
            log.info("消息发送成功：{}", message);
        } catch (IOException e) {
            log.error("消息发送失败", e);
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(MessageDTO message) throws IOException {
        if (message.getReceiveUserId() != null && !"".equals(message.getReceiveUserId())) {
            // 全局发送消息
            if ("0".equals(message.getReceiveUserId())) {
                socketMap.forEach((k, v) -> {
                    for (WebSocketServer item : v) {
                        item.sendMessage(JSONObject.toJSONString(message));
                    }
                });
            } else if (socketMap.get(message.getReceiveUserId()) != null) {
                for (WebSocketServer item : socketMap.get(message.getReceiveUserId())) {
                    item.sendMessage(JSONObject.toJSONString(message));
                }

            } else {
                log.error("没有找到消息发送的目标对象");
            }

        } else {
            log.error("没有找到消息发送的目标对象");
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
