package com.example.messagingstompwebsocket.socket;
import static cn.hutool.json.JSONUtil.toBean;
import static cn.hutool.json.JSONUtil.toJsonStr;
import static java.util.Objects.requireNonNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

/**
 * WebSocket客户端状态监听
 */
@Slf4j
@Component
public class WebSocketEventListener {

    /**
     * 监听客户端连接
     *
     * @param event 连接事件对象
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已连接: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 会话ID: " + requireNonNull(event.getMessage().getHeaders().get("simpSessionId")) + " }");
    }

    /**
     * 监听客户端关闭事件
     *
     * @param event 关闭事件对象
     */
    @EventListener
    public void handleWebSocketCloseListener(SessionDisconnectEvent event) {
        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已关闭: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 会话ID: " + requireNonNull(event.getMessage().getHeaders().get("simpSessionId")) + " }");
        WebSocketCache.remove(
                String.valueOf(event.getMessage().getHeaders().get("simpSessionId"))
        );
    }

    /**
     * 监听客户端订阅事件
     *
     * @param event 订阅事件对象
     */
    @EventListener
    public void handleSubscription(SessionSubscribeEvent event) {
        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已订阅: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 订阅节点: " + requireNonNull(event.getMessage().getHeaders().get("simpDestination")) + " }");
            WebSocketCache.add(
                    String.valueOf(event.getMessage().getHeaders().get("simpSessionId")) ,
                    String.valueOf(event.getMessage().getHeaders().get("simpDestination"))
                    );
    }

    /**
     * 监听客户端取消订阅事件
     *
     * @param event 取消订阅事件对象
     */
    @EventListener
    public void handleUnSubscription(SessionUnsubscribeEvent event) {
        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已取消订阅: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 取消订阅节点: " + requireNonNull(event.getMessage().getHeaders().get("simpSessionId")) + " }");
        WebSocketCache.remove(
                String.valueOf(event.getMessage().getHeaders().get("simpSessionId"))
        );
    }
}
