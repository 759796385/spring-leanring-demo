package com.newtonk.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类名称：
 * 类描述：
 1. 写入数据到Buffer
 2. 调用flip()方法
 3. 从Buffer中读取数据
 4. 调用clear()方法或者compact()方法

 当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，需要通过flip()方法将
 Buffer从写模式切换到读模式。在读模式下，可以读取之前写入到buffer的所有数据。

 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
 有两种方式能清空缓冲区：调用clear()或compact()方法。clear()方法会清空整个缓冲区。
 compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区
 未读数据的后面。


 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，
 并提供了一组方法，用来方便的访问该块内存。

 熟悉它的三个属性：

 capacity:作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，
 char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。

 position:当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据
 写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1。

 当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0。
 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
 limit:在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
 当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模
 式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就
 是position）

 NIO 有以下Buffer类型：

 ByteBuffer
 MappedByteBuffer
 CharBuffer
 DoubleBuffer
 FloatBuffer
 IntBuffer
 LongBuffer
 ShortBuffer
 * 创建人：newtonk
 *
 *
 * 创建日期：2017/10/25
 */
public class BufferDemo {

    /**
     * 写数据到Buffer有两种方式：

     从Channel写到Buffer。
     通过Buffer的put()方法写到Buffer里。
     */
    void wirteDemo() throws Exception {
        FileChannel inChannel = getChanel();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf); //read into buffer.

        /*put 很多版本，支持往不同的位置写入*/
        buf.put((byte) 123);
        //调用flip()方法会将position设回0，并将limit设置成之前position的值。
        buf.flip();

    }

    /**
     * 从Buffer中读取数据有两种方式：

     从Buffer读取数据到Channel。
     使用get()方法从Buffer中读取数据。

     */
    void readDemo() throws Exception{
        FileChannel inChannel = getChanel();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesWritten = inChannel.write(buf);
        //get方法有很多版本，允许你以不同的方式从Buffer中读取数据。
        byte aByte = buf.get();
        //Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。
        // limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
        buf.rewind();

        /* 当buf数据被读完，就需要重置啦 */
        //clear()方法，position将被设回0，limit被设置成 capacity的值。换句话说，Buffer 被清空了。
        // Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
        buf.clear();
        //compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
        // limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
        buf.compact();

        //通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。
        // 之后可以通过调用Buffer.reset()方法恢复到这个position。例如：
        buf.mark();

//call buffer.get() a couple of times, e.g. during parsing.

        buf.reset();  //set position back to mark.
    }


    FileChannel getChanel() throws Exception{
        RandomAccessFile aFile = new RandomAccessFile("java8-study/src/main/resource/demo.txt", "rw");
        /* 获取文件通道 */
        return aFile.getChannel();
    }
}
