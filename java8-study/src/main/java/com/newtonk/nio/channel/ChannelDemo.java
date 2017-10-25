package com.newtonk.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类名称：
 * 类描述：
 *
 *
 FileChannel：从文件中读写数据。
 DatagramChannel：能通过UDP读写网络中的数据。
 SocketChannel：能通过TCP读写网络中的数据。
 ServerSocketChannel：可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。
 * 创建人：newtonk
 * 创建日期：2017/10/25
 */
public class ChannelDemo {
    public static void main(String[] args) throws IOException {
        transferDemo();
    }

    public static void readDemo() throws IOException{
        RandomAccessFile aFile = new RandomAccessFile("java8-study/src/main/resource/demo.txt", "rw");
        /* 获取文件通道 */
        FileChannel inChannel = aFile.getChannel();
        /* 申请48长度的缓冲 */
        ByteBuffer buf = ByteBuffer.allocate(48);
        /* 从通道中读入到缓冲 */
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("\n Read " + bytesRead);
            /* 翻转缓冲区。 极限指针到当前位置，位置指针到0.  写完用于读 */
            buf.flip();
            /*判断缓冲区还有剩余的指针。 即位置指针还没到极限指针*/
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            /*清空缓冲区*/
            buf.clear();

            /* 从通道中获取字符到缓冲 */
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }


    public static void transferDemo() throws IOException{
        RandomAccessFile fromFile = new RandomAccessFile("java8-study/src/main/resource/demo.txt", "rw");
        RandomAccessFile toFile = new RandomAccessFile("java8-study/src/main/resource/to.txt", "rw");

        FileChannel      fromChannel = fromFile.getChannel();
        FileChannel      toChannel = toFile.getChannel();


        long position = 0;
        long count = fromChannel.size();
        //position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。
        // 如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。
        toChannel.transferFrom(fromChannel,position,count);
    }
}
