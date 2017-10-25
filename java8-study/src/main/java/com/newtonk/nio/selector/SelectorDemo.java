package com.newtonk.nio.selector;

import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 类名称：
 * 类描述：
 Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
 这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。


 * 创建人：newtonk
 * 创建日期：2017/10/25
 */
public class SelectorDemo {

    public static void main(String[] args) {

    }

    public static void selectTest() throws Exception {
        Selector selector = Selector.open();
        SocketChannel channel =  getChanel();

        //向Selector注册通道
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector,
                SelectionKey.OP_READ);
        //与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，
        // 因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。
        //第二个参数 意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：
        //Connect、Accept、Read、Write
        // 多个事件 组合 SelectionKey.OP_READ | SelectionKey.OP_WRITE;

        /* 可从SelectionKey中查看监听事件集合 */
        int interestSet = key.interestOps();

        boolean isInterestedInAccept  = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
        boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;

        /* ready 集合是通道已经准备就绪的操作的集合。在一次选择(Selection)之后，你会首先访问这个ready set。 */
        int readySet = key.readyOps(); // 同上面的位操作
        key.isAcceptable();
        key.isConnectable();

        Channel socketChannel  = key.channel(); //SelectionKey中获取通道
        Selector getSelector = key.selector();//SelectionKey获取选择器

        /* 附加对象 可以将一个对象或者更多信息附着到SelectionKey上
        ，这样就能方便的识别某个给定的通道。例如，可以附加 与通道一起使用的Buffer，或是包含聚集数据的某个对象。*/
        key.attach(new Object());
        Object attachedObj = key.attachment();
        //当然register也支持附加对象的构造参数
    }

    static SocketChannel getChanel() throws Exception{
        return null;
    }
}
