package com.newtonk.public_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/12/27 0027
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    /**
     * RabbitMQ中的exchange类型有这么几种：direct，topic，headers以及fanout。
     *  fanout:它会向所有的queue广播所有收到的消息。
     *  direct：消息将会被传递到与它的routing key完全相同的 binding key的queue中。
     */
    public static void main(String[] argv)
            throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(6043);
        factory.setUsername("fepapi_dev_s9kEo2");
        factory.setPassword("RJm69M1cB627");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /* 声明一一个exchange，必须 */
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String message = "123456";//getMessage(argv);
        /*  第一个为exchange的名字，使用空字符串表示使用默认的无名的exchange：如果有的话 消息将根据routingKey被发送到指定的queue中。
         * fanout类型的exchange会忽略routingKey */
        //这里的路由我们使用“”，由于使用fanout，所有该exchange下的队列都将收到消息
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        /* 如果还没有queue被绑定到exchange上，那么消息将会丢失，但这对我们来说是可以接收的，如果没有consumer正在监听消息， 那么可以安全的丢弃这些消息。 */
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
