package com.newtonk.nio.channel;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/10/29 0029
 */
public class FileChannelDemo {
    /* NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。

      FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。 */
    public static void main(String[] args) {

    }

    public void fileApi() throws Exception {
       FileChannel fileChannel = ChannelDemo.getChannel();
       /* 读数据 */
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = fileChannel.read(buf);//返回值表示读了多少字节， -1表示到末尾

        /* 写数据*/
        buf.put(new byte[]{1,2,3,45});
        buf.flip();//切换到读模式
        while(buf.hasRemaining()) {
            fileChannel.write(buf); //无法保证write()方法一次能向FileChannel写入多少字节，因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
        }

        fileChannel.close();

        long pos = fileChannel.position();//返回当前文件位置
        //位置设置在文件结束符之后，然后试图从文件通道中读取数据，读方法将返回-1 —— 文件结束标志。
        fileChannel.position(pos +123); //设置文件位置
        //将位置设置在文件结束符之后，然后向通道中写数据，文件将撑大到当前位置并写入数据。这可能导致“文件空洞”，磁盘上物理文件中写入的数据间有空隙。

        //FileChannel实例的size()方法将返回该实例所关联文件的大小
        long fileSize = fileChannel.size();

        fileChannel.truncate(100); //方法截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除

        /* 出于性能方面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。
        要保证这一点，需要调用force()方法。 如同Linux下的sync  */
        fileChannel.force(true);
    }
}
