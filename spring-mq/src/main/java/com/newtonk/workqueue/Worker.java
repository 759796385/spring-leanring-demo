package com.newtonk.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 类名称：消费者  生产者-NewTask
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/12/27 0027
 */
public class Worker {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(6043);
        factory.setUsername("fepapi_dev_s9kEo2");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        boolean  durable = true;//消息持久化  不能修改已经声明为不持久化的queue为持久化
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        /* 公平分发。当两个消费者消费速度不一致时，但是依然会公平获取消息，导致局部负载过大。
         * 用basicQos方法来设置prefetchCount = 1，一次只取一条消息消费，直到发送ack才消费下一条消息 */
        channel.basicQos(1);//一次只取一条消息消费
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    //是模拟的耗时任务
                        doWork(message);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                    /*当consumer处理完成之后，向rabbitMQ发送ack*/
                    /* 只有当ack发送后，这条消息才会真正被消费掉，不然会一直转发给下一个消费者 */
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false; // 自动发送ack
        /* 开始消费 */
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}
