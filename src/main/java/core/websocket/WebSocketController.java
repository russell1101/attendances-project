package core.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws/attendance")
public class WebSocketController {
	private static final Set<Session> SESSION_SET = Collections.synchronizedSet(new HashSet<>());

	@OnOpen
	public void onOpen(Session session) {
		SESSION_SET.add(session);
		System.out.println("WebSocket連線:" + session.getId());
	}

	@OnClose
	public void onClose(Session session) {
		SESSION_SET.remove(session);
		System.out.println("WebSocket斷線:" + session.getId());
	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("WebSocket錯誤:" + error.getMessage());
	}

	@OnMessage
	public void onMessage(String message) {
		for (Session session : SESSION_SET) {
			if (session.isOpen()) {

				session.getAsyncRemote().sendText(message);

			} else {
				SESSION_SET.remove(session);
			}
		}
	}

}
