package web.chart.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChartWebSocket extends TextWebSocketHandler {

    private static Set<WebSocketSession> sessions =
        Collections.synchronizedSet(ConcurrentHashMap.newKeySet());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("圖表頁已連線，目前連線數：" + sessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, 
            org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        System.out.println("圖表頁已斷線，目前連線數：" + sessions.size());
    }

    public static void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    System.out.println("廣播失敗：" + e.getMessage());
                }
            }
        }
    }
}