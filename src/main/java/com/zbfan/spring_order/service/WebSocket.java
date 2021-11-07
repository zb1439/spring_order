package com.zbfan.spring_order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("[WebSocket Info]: new session added, total sessions {}", webSocketSet.size());
    }

    @OnClose
    public void onClose(Session session) {
        webSocketSet.remove(this);
        log.info("[WebSocket Info]: connection closed, total session {}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String msg) {
        log.info("[WebSocket Info]: received info from client: {}", msg);
    }

    public void sendMessage(String msg) {
        for (WebSocket webSocket : webSocketSet) {
            log.info("[WebSocket Info]: broadcast info, message={}", msg);
            try {
                webSocket.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
