package com.newtonk.nio.demo;

import com.newtonk.nio.channel.ChannelDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/10/29 0029
 */
public class TcpDemo {
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
                    /* 服务器监听接口 */
            ServerSocketChannel listenerChannel=ServerSocketChannel.open();
            listenerChannel.socket().bind(new InetSocketAddress(8888));

            listenerChannel.configureBlocking(false);
            /* 注册服务器channel到选择器 */
            listenerChannel.register(selector,SelectionKey.OP_ACCEPT );
            while(true) {
                /* 等待通道就绪 */
                if (selector.select(1000) == 0) {
                    System.out.print("等待中....");
                    continue;
                }
                /* 返回就绪通道 */
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
                        // 从客户端读取数据
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        ChannelDemo.readBuffer(channel,byteBuffer);
                    } else if (key.isWritable()) {
                        // 客户端可写
                    }
                    /* 处理完通道后 移除 */
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
                    buffer.flip();
                    while(buffer.hasRemaining()) {
                        channel.write(buffer);
                    }
                    System.out.println("客户端发送 "+ Arrays.toString(as));
                    Thread.sleep(10000);
                    /* 缓冲填充数据 */
                    byte[] bs = new byte[]{9,8,7,6,5,4,3,2,1};
                    buffer.clear();
                    buffer.put(bs);
                    buffer.flip();
                    while(buffer.hasRemaining()) {
                        channel.write(buffer);
                    }
                    System.out.println("客户端发送 "+ Arrays.toString(bs));
                }
            }
        }
    }
}
