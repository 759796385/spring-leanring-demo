package com.example.messagingstompwebsocket.client;

import com.example.messagingstompwebsocket.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

@Slf4j
public class WebClient {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setDefaultHeartbeat(new long[]{20000, 0});
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler()); // for heartbeats

        String url = "ws://127.0.0.1:8080/gs-guide-websocket";
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        ListenableFuture<StompSession> stompSessionFuture = stompClient.connect(url, sessionHandler);

        StompSession stompSession = stompSessionFuture.get();
        stompSession.setAutoReceipt(true);
        System.out.println("connect sucess");


        log.info("subscribe success...");
        HelloMessage helloMessage = new HelloMessage("我连接上啦");
        stompSession.send("/app/hello", helloMessage);

        log.info("send msg success");
        Thread.sleep(1000 * 3);

        stompSession.subscribe("/topic/greetings", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info("msg:" + payload);
            }
        });
        Thread.sleep(8 * 60 * 1000);
    }
}
