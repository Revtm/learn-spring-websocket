package com.revtm.websocketchat.config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic/", "/queue/");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat").withSockJS();

        registry.addEndpoint("/private-chat")
                .setHandshakeHandler(new DefaultHandshakeHandler(){
                    public boolean beforeHandshake(
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            WebSocketHandler wsHandler,
                            Map attributes
                    ){
                        if(request instanceof ServletServerHttpRequest){
                            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
                            HttpSession session = servletServerHttpRequest.getServletRequest()
                                    .getSession();
                            attributes.put("sessionId", session.getId());
                        }
                        return true;
                    }
                });
        registry.addEndpoint("/private-chat").setHandshakeHandler(new DefaultHandshakeHandler(){
            public boolean beforeHandshake(
                    ServerHttpRequest request,
                    ServerHttpResponse response,
                    WebSocketHandler wsHandler,
                    Map attributes
            ){
                if(request instanceof ServletServerHttpRequest){
                    ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
                    HttpSession session = servletServerHttpRequest.getServletRequest()
                            .getSession();
                    log.info(session.getId());
                    attributes.put("sessionId", session.getId());
                }
                return true;
            }
        }).withSockJS();
    }
}
