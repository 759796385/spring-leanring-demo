package com.newtonk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by newtonk on 2017/6/18.
 */
@RestController
@RequestMapping(value = "/asyn")
public class AsyncController {


    /**
     * 请求时，Servlet 容器线程直接返回一个callable然后切换，可提高接口吞吐量。
     * 然后由其他线程去计算Callable，当Callable返回，Servlet 容器线程恢复处理这个请求，返回对应值
     * 注意：两次线程调用都会触发声明好的 @ModelAttribute方法
     */
    @GetMapping(value = "")
    public Callable<String> get(){
        System.out.println("get request start");
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "return";
            }
        };
    }

    private static final ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);  ;

    /**
     * 注意，此接口后继请求还是被阻塞的。 Servlet contain thread可以继续处理的是其他requestMapping
     */
    @GetMapping("/deferred")
    public DeferredResult<String> quotes() {
        System.out.println("quotes request start");
        DeferredResult<String> deferredResult = new DeferredResult<String>();
        cachedThreadPool.execute(new Task(deferredResult));
        return deferredResult;
    }
    class Task implements Runnable {
        private DeferredResult<String> deferredResult;
        public Task(DeferredResult<String> stringDeferredResult){
            this.deferredResult = stringDeferredResult;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread over~");
            deferredResult.setResult("result");
        }
    }
}
