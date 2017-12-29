package com.newtonk.public_subscribe;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/12/28 0028
 */
public class TopicReceive {
    /**
     * 发送到topic exchange中的消息不能有一个任意的routing_key——它必须是一个使用点分隔的单词列表。
     *  对应路由key的消息只能由对应key的消费者接收，key可以使用通配符
     *  *可以代替任意一个单词
         #可以代替0个或多个单词
        注意：以点分隔的路由匹配严格匹配长度，如果一个key匹配是三个长度，发送四个长度的key，即使前三个完全匹配，这条消息也是匹配不上的。
     */
    public static void main(String[] args) {

    }
}
