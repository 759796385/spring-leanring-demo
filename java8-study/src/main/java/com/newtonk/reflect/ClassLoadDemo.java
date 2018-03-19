package com.newtonk.reflect;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2018/3/2 0002
 */
public class ClassLoadDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = ClassLoadDemo.class.getClassLoader();
        System.out.println(loader);
        //使用ClassLoader.loadClass()来加载类，不会执行初始化块
//        loader.loadClass("com.newtonk.reflect.Test");

        //使用Class.forName()来加载类，默认会执行初始化块
//        Class.forName("com.newtonk.reflect.Test");

        //使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
        Class.forName("com.newtonk.reflect.Test", false, loader);

    }
}
