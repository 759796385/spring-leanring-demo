package com.newtonk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

/**
 * Created by newtonk on 2017/6/18.
 */
@RequestMapping("/stream")
@RestController
public class HttpStreamController {

    /**
     * 注意response的head中
     * Transfer-Encoding:chunked 数据是分块的
     */
    @RequestMapping("/events")
    public ResponseBodyEmitter handle() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        Thread thread = new Thread(new Task(emitter));
        thread.start();
        return emitter;
    }

    class Task implements Runnable {
        private ResponseBodyEmitter emitter;
        public Task(ResponseBodyEmitter emitter){
            this.emitter = emitter;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                emitter.send("hello");
                Thread.sleep(2000);
                emitter.send("- world");
                Thread.sleep(2000);
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread over~");
        }
    }
}
