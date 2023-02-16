package com.example.messagingstompwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MessagingStompWebsocketApplication {

	public static void main(String[] args) {

		String a = "/user/1/message";
		String b = a.substring(0, a.lastIndexOf("/"));
		System.out.println(b.substring(b.lastIndexOf("/")+1));
		SpringApplication.run(MessagingStompWebsocketApplication.class, args);
	}
}
