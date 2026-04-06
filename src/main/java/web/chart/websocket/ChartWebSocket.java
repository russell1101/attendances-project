package web.chart.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/ws/admin/chart")
public class ChartWebSocket {

    private static Set<Session> sessions =
        Collections.synchronizedSet(ConcurrentHashMap.newKeySet());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("圖表頁已連線，目前連線數：" + sessions.size());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("圖表頁已斷線，目前連線數：" + sessions.size());
    }

    public static void broadcast(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.out.println("廣播失敗：" + e.getMessage());
                }
            }
        }
    }
}