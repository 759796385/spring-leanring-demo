package com.newtonk.thread.threadsafe;

/**
 * Created by newtonk on 2017/6/29.
 */
public class NoVisibility {
    private static boolean read;
    private static int number;

    private static class ReaderThread extends Thread{
        public void run(){
            while(!read){
//                Thread.yield();//暂停当前正在执行的线程对象，并执行其他线程。
                System.out.println(number);
            }

        }
    }
    public static void main(String[] args){
        Thread thread = new ReaderThread();
        thread.start();
        number=43;
        read = true;
        //1.发生重排序  很可能什么都输出不了
        //2. 死循环，可能读不到read的值。
    }
}
