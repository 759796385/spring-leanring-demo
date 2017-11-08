package com.newtonk.nio.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.CountDownLatch;

/**
 * 接口名称：
 * 接口描述：
 * 在概念上，Java的管道不同于Unix/Linux系统中的管道。在Unix/Linux中，
 * 运行在不同地址空间的两个进程可以通过管道通信。在Java中，通信的双方应该是运行在同一进程中的不同线程。
 * 创建人：tq
 * 创建日期：2017/10/29 0029
 */
public class PipeDemo {
    /*  管道是2个线程之间的单向数据连接(线程间通信)。Pipe有一个source通道和一个sink通道。数据会被写到sink(下沉)通道，从source通道读取。  */
    public void PipeApi() throws Exception {
        /* 管道开启 */
        Pipe pipe = Pipe.open();
        /* 向管道写数据 */
        Pipe.SinkChannel sinkChannel = pipe.sink();

        ByteBuffer buffer = ByteBuffer.allocate(48);
        byte[] as = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        buffer.put(as);
        buffer.flip();
        while (buffer.hasRemaining()) {
            sinkChannel.write(buffer);
        }

        /* 管道读数据 */
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = sourceChannel.read(buf);

    }

    public static void main(String[] args) throws Exception {
        final Pipe pipe = Pipe.open();
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                /* 请务必关闭流 操作管道时，需要保证读写管道的线程都不退出，否则，会出现write/read end dead的异常。*/
                try(Pipe.SinkChannel sinkChannel = pipe.sink()) {
                    ByteBuffer buffer = ByteBuffer.allocate(48);
                    byte[] as = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
                    buffer.put(as);
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        sinkChannel.write(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Pipe.SourceChannel sourceChannel = pipe.source();) {
                    sourceChannel.configureBlocking(false);
                    ByteBuffer buf = ByteBuffer.allocate(48);
                    countDownLatch.await();
                    int bytesRead = sourceChannel.read(buf);
                    while (bytesRead > 0) {
                        buf.flip();
                        while (buf.hasRemaining()) {
                            System.out.print(buf.get());
                        }
                        buf.clear();
                        bytesRead = sourceChannel.read(buf);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
