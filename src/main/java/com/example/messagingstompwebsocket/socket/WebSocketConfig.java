package com.example.messagingstompwebsocket.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		//应用程序以/app为前缀，代理目的地以/topic、/queue为前缀
		config.enableSimpleBroker("/topic", "/user");
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/user");

	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket")
				//设置跨域
				.setAllowedOrigins("*")
				//自定义
				.setHandshakeHandler(new CustomHandshakeHandler())
				.withSockJS();
	}

}
