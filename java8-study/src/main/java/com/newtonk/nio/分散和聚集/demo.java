package com.newtonk.nio.分散和聚集;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类名称：
 * 类描述：
  分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
   因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
  聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，
    因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
 * 创建人：newtonk
 * 创建日期：2017/10/25
 */
public class demo {
    private FileChannel channel;

    /**
     * 分散
     */
    public void scatter() throws IOException {
        //。read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写。
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };
        channel.read(bufferArray);
        //只有当前buffer填满才会移动到下面的buffer，不太适合动态消息体。
    }

    public void gather() throws IOException {
        //write()方法会按照buffer在数组中的顺序，将数据写入到channel，注意只有position和limit之间的数据才会被写入。
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };

        channel.write(bufferArray);
        //比较适合动态消息，因为只会写入缓冲实际大小的字节
    }
}
