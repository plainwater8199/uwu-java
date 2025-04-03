package com.uwu.study.netty.ws.websocketconfigurer.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ServerWebSocketHandler extends TextWebSocketHandler {
    /**
     * WebSocketSession  代表一个客户端连接 session.sendMessage()
     */


    /**
     * WebSocket 服务端需要同时处理：
     *       连接建立（afterConnectionEstablished）
     *       连接关闭（afterConnectionClosed）
     *       消息发送（遍历 sessions 广播消息）
     *     这些操作可能由 不同线程 同时触发（例如多个客户端同时连接/断开）。
     *
     *
     * CopyOnWriteArrayList 的特性
     * 1、写时复制：修改操作（如 add/remove）会创建新数组副本、读多写少的场景
     * 2、线程安全：无需额外同步代码、高并发环境
     * 3、弱一致性：迭代器遍历时能反映初始状态，不保证后续修改可见、允许短暂数据不一致
     * 对于 WebSocket 的 sessions 管理：
     *      写入频率：较低（仅在连接建立/断开时修改）
     *      读取频率：极高（每次广播消息需遍历所有会话）
     *
     *
     * 为什么不用 ConcurrentHashMap？
     * 虽然 ConcurrentHashMap 也适合高并发场景，但：
     *        列表结构更直观：会话管理通常需要遍历，而非按键查询
     *        内存开销更小：CopyOnWriteArrayList 在元素较少时更节省内存
     */

    // 线程安全的会话列表
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("[服务端] 新连接: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("[服务端] 收到消息: " + payload);

        // 广播给所有客户端
        for (WebSocketSession s : sessions) {
            try {
                s.sendMessage(new TextMessage("ECHO: " + payload));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("[服务端] 连接关闭: " + session.getId());
    }


    // 向所有客户端广播消息
    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    // 向特定客户端发送消息
    public void sendToClient(String sessionId, String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.getId().equals(sessionId) && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
                break;
            }
        }
    }

}
