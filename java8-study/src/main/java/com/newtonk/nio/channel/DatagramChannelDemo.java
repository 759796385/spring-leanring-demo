package com.newtonk.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/10/29 0029
 */
public class DatagramChannelDemo {
    /* 这货是个UDP包的通道 */

    public void udpTest() throws Exception{
        DatagramChannel channel = DatagramChannel.open();
        /* 监听UDP端口 */
        channel.socket().bind(new InetSocketAddress(9999));

        /*接受数据*/
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        channel.receive(buf);//如果Buffer容不下收到的数据，多出的数据将被丢弃。

        buf.put(new byte[]{1,2,3,45});
        buf.flip();//切换到读模式
        int bytesSent = channel.send(buf, new InetSocketAddress("newtonk.com", 80));
        //你只知道你发了数据，但你不能确保结果

    }
}
