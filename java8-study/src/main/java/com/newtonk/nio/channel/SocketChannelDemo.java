package com.newtonk.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/10/29 0029
 */
public class SocketChannelDemo {
    /* NIO中的SocketChannel是一个连接到TCP网络套接字的通道。可以通过以下2种方式创建SocketChannel：

       1. 打开一个SocketChannel并连接到互联网上的某台服务器。
       2. 一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。*/
    public static void main(String[] args) {

    }

    /**
     * 客户端通道
     */
    public void SocketChannelTest() throws Exception{
        /* 打开一个客户端 */
        SocketChannel socketChannel = SocketChannel.open();

        /* channel为非阻塞模式 就可以在异步模式下调用connect(),read() 和write()了。 */
        socketChannel.configureBlocking(false);//

        /* 异步模式下，在建立连接后就返回了。 为了确认是否建立，可以调用finishConnect()的方法 */
        socketChannel.connect(new InetSocketAddress("http://newtonk.com", 80));
        while(! socketChannel.finishConnect() ){
            //wait, or do something else...
        }


        /* 读取数据 */
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = socketChannel.read(buf); //read()方法返回的int值表示读了多少字节进Buffer里。
        // 如果返回的是-1，表示已经读到了流的末尾（连接关闭了）。
        //非阻塞模式下,read()方法在尚未读取到任何数据时可能就返回了。所以需要关注它的int返回值，它会告诉你读取了多少字节。

        /* 写数据 */
        buf.put(new byte[]{1,2,3,45});
        buf.flip();//切换到读模式
        while(buf.hasRemaining()) { //非阻塞模式下，write()方法在尚未写出任何内容时可能就返回了。所以需要在循环中调用write()。
            socketChannel.write(buf); //无法保证write()方法一次能向FileChannel写入多少字节，因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
        }


        /* 关闭数据 */
        socketChannel.close();
    }

    /**
     * 服务端通道
     */
    public void ServerSocketTest() throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        serverSocketChannel.configureBlocking(false);

        /* 阻塞模式下 需要一直监听连接 */
        /**
         while(true){
         SocketChannel socketChannel =
         serverSocketChannel.accept();

         //do something with socketChannel...
         }
         * */

        /* 我们通常设置成非阻塞模式。。在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接，返回的将是null。
         因此，需要检查返回的SocketChannel是否是null。 */
        while(true){
            SocketChannel socketChannel =
                    serverSocketChannel.accept();

            if(socketChannel != null){
                //do something with socketChannel...
            }
        }

        /* ServerSocketChannel.close() 方法来关闭ServerSocketChannel. */
//        serverSocketChannel.close();
    }
}
