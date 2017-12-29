package com.newtonk.public_subscribe;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/12/28 0028
 */
public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(6043);
        factory.setUsername("fepapi_dev_s9kEo2");
        factory.setPassword("RJm69M1cB627");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        /* 无参数的queueDeclare()方来来创建一个非持久化的、独有的并且是自动删除的已命名的queue。 */
        String queueName = channel.queueDeclare().getQueue();

        /* 这里创建的是一个非持久化独有且会自动删除的queue，我们也可以按之前配置绑定创建的queue */
//        boolean  durable = true;//消息持久化  不能修改已经声明为不持久化的queue为持久化
//        channel.queueDeclare("指定queue名字", durable, false, false, null);

        //这里我们绑定路由为"" ，将监听往“”路由发送的消息，支持多重绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "routing");
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
