package com.newtonk.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2018/4/8 0008
 */
public class MyClassLoader extends URLClassLoader {

    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }


    public MyClassLoader(URL[] urls) {
        super(urls);
    }


    public MyClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public static void main(String[] args) {
        URL deleteDemo = Thread.currentThread().getContextClassLoader().
                getResource("config/deletedemo2.jar");
//        URL deleteDemo = Thread.currentThread().getContextClassLoader().
//                getResource("deletedemo2.jar");
        //父loader是 appclassloader
        MyClassLoader myClassLoader = new MyClassLoader(new URL[]{deleteDemo},
                Thread.currentThread().getContextClassLoader());
        try {
            Class c= myClassLoader.loadClass("com.DemoImpl");
            if(c!= null){
                try {
                    Object obj = c.newInstance();
                    Method method = c.getDeclaredMethod("print",null);
                    method.invoke(obj,null);
                } catch (InstantiationException | IllegalAccessException
                        | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
