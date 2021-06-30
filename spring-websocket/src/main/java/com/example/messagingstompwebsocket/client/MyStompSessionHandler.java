package com.example.messagingstompwebsocket.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        // ...


//        session.send("/app/hello", "payload");
    }

    public void handleFrame(StompHeaders headers, @Nullable Object payload) {
        log.info("接收订阅消息=" + (String) payload);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable exception) {
        log.error("",exception);
        //super.handleTransportError(stompSession, exception);
        try {
            Thread.sleep(3000);
            System.out.println("我断开啦");
        } catch (InterruptedException e) {
            log.error("",e);
        }
    }
}
