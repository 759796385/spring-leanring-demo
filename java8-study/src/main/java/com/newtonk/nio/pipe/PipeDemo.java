package com.newtonk.nio.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 接口名称：
 * 接口描述：
 * 创建人：tq
 * 创建日期：2017/10/29 0029
 */
public class PipeDemo {
    /*  管道是2个线程之间的单向数据连接(线程间通信)。Pipe有一个source通道和一个sink通道。数据会被写到sink(下沉)通道，从source通道读取。  */
    public void PipeApi() throws Exception{
        /* 管道开启 */
        Pipe pipe = Pipe.open();
        /* 向管道写数据 */
        Pipe.SinkChannel sinkChannel = pipe.sink();

        ByteBuffer buffer = ByteBuffer.allocate(48);
        byte[] as = new byte[]{1,2,3,4,5,6,7,8};
        buffer.put(as);
        buffer.flip();
        while(buffer.hasRemaining()) {
            sinkChannel.write(buffer);
        }

        /* 管道读数据 */
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = sourceChannel.read(buf);

    }
}
