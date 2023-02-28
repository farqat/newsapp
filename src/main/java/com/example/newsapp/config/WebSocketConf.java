package com.example.newsapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConf implements WebSocketMessageBrokerConfigurer {

    @Value("${broker.host}")
    private String host;

    @Value("${broker.port}")
    private int port;

    @Value("${broker.username}")
    private String user;

    @Value("${broker.password}")
    private String pass;

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
       registry.addEndpoint("/ws-register")
               .setAllowedOriginPatterns("*")
               .withSockJS();
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/queue", "/topic")
                .setRelayHost(host)
                .setRelayPort(port)
                .setClientLogin(user)
                .setClientPasscode(pass)
                .setSystemLogin(user)
                .setSystemPasscode(pass)
                .setUserDestinationBroadcast("/topic/unresolved-user")
                .setUserRegistryBroadcast("/topic/log-user-registry");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
