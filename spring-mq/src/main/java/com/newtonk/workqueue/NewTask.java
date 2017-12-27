package com.newtonk.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/12/27 0027
 */
public class NewTask {
    private static final String QUEUE_NAME = "hello";

    /**
     * RabbitMQ会顺序的把消息发送到下一个Consumer，上面打印出的消息也印证了这一点。
     * 平均来说每个Consumer接收到的消息数量是相同的，这种发送消息的方式称为循环发送(round-robin)
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        boolean  durable = true;//消息持久化  不能修改已经声明为不持久化的queue为持久化
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        String message = getMessage(args);
        message="1.2.3.4.5.6.7.8.9.10";
        /* 发布消息也要持久化配置 */
        channel.basicPublish("", QUEUE_NAME,  MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
