package com.newtonk.nio.selector;

import com.newtonk.nio.channel.ChannelDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 类名称：
 * 类描述：
 Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
 这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。


 * 创建人：newtonk
 * 创建日期：2017/10/25
 */
public class SelectorDemo {

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                server();
            }
        }).start();
    }

    public static void server() {
        try {
                    /* 建立选择器 */
            Selector selector = Selector.open();
                    /* 服务器坚挺接口 */
            ServerSocketChannel listenerChannel=ServerSocketChannel.open();
            listenerChannel.socket().bind(new InetSocketAddress(8888));

            listenerChannel.configureBlocking(false);
            listenerChannel.register(selector,SelectionKey.OP_ACCEPT );
            while(true) {
                if (selector.select(1000) == 0) {
                    System.out.print("等待中....");
                    continue;
                }
                Iterator<SelectionKey> keyIter=selector.selectedKeys().iterator();
                while(keyIter.hasNext()) {
                    SelectionKey key = keyIter.next();

                    if (key.isAcceptable()) {
                        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                        // 有客户端连接请求时 注册到选择器
                        channel.configureBlocking(false);
                        channel.register(key.selector(), SelectionKey.OP_READ|SelectionKey.OP_WRITE, ByteBuffer.allocate(9));
                    } else if (key.isConnectable()) {
                        // 客户端连接上
                    } else if (key.isReadable()) {
                        SocketChannel  channel = (SocketChannel) key.channel();
                        // 从客户端读取数据
                        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        ChannelDemo.readBuffer(channel,byteBuffer);
                    } else if (key.isWritable()) {
                        // 客户端可写
                    }
                    keyIter.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void client() throws Exception{
        /* 创建选择器 */
        Selector selector = Selector.open();

        // 打开监听信道
        SocketChannel channel=SocketChannel.open(new InetSocketAddress("localhost", 8888));
        channel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE,byteBuffer);
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) keyIterator.next();
                if(selectionKey.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

//                    /* 缓冲填充数据 */
                    byte[] as = new byte[]{1,2,3,4,5,6,7,8};
                    buffer.put(as);
                    channel.write(buffer);

                    Thread.sleep(1000);
                    /* 缓冲填充数据 */
                    byte[] bs = new byte[]{9,8,7,6,5,4,3,2,1};
                    buffer.clear();
                    buffer.put(as);
                    channel.write(buffer);
                }
            }
        }
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

    /**
     * selector注册通道后
     */
    public void selectorDemo(Selector selector){
        //int select() 阻塞到至少有一个通道在你注册的事件上就绪了。
        // select()方法返回的int值表示有多少通道已经就绪。亦即，自上次调用select()方法后有多少通道变成就绪状态。
        //int select(long timeout) 和select()一样，除了最长会阻塞timeout毫秒(参数)。
        //int selectNow() 不会阻塞，不管什么通道就绪都立刻返回.没有通道变成可选择的，则此方法直接返回零。

        Set selectedKeys = selector.selectedKeys();//获取访问就绪的通道
        Iterator keyIterator = selectedKeys.iterator();

    }




    static SocketChannel getChanel() throws Exception{
        return null;
    }
}
