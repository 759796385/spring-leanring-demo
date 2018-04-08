package com.newtonk.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2018/4/8 0008
 */
public class MyClassLoader extends URLClassLoader {

    private ClassLoader childClassLoader;

    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public void setChildClassLoader(ClassLoader child){
        this.childClassLoader = child;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(childClassLoader!=null){
            return childClassLoader.loadClass(name);
        }
        return super.loadClass(name);
    }

    public static void main(String[] args) {
        MyClassLoader child  = parentDependChildWithHierarchical();
        try {
            //child 中的包依赖 myClassLoader中的包
            Class c= child.loadClass("com.dependDemo");
            if(c!= null){
                try {
                    Object obj = c.newInstance();
                    Method method = c.getDeclaredMethod("depend",null);
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

    /**
     * 依赖的包在子loader中，如何解决扫描不到的问题!!!
     */
    private static MyClassLoader parentDependChildWithHierarchical(){
        URL depend = Thread.currentThread().getContextClassLoader().
                getResource("dependDemo.jar");
        //父loader是 appclassloader
        MyClassLoader myClassLoader = new MyClassLoader(new URL[]{depend},
                Thread.currentThread().getContextClassLoader());
        URL deleteDemo1 = Thread.currentThread().getContextClassLoader().
                getResource("deletedemo.jar");
        MyClassLoader child = new MyClassLoader(new URL[]{deleteDemo1},myClassLoader);
        myClassLoader.setChildClassLoader(child);
        return child;
    }

    /**
     * 当父loader依赖的包在子loader中，全盘委托只会查找父级以及他以上的，搜索不到子loader，报classNotFound
     */
    private static MyClassLoader parentDependChild(){
        URL depend = Thread.currentThread().getContextClassLoader().
                getResource("dependDemo.jar");
        //父loader是 appclassloader
        MyClassLoader myClassLoader = new MyClassLoader(new URL[]{depend},
                Thread.currentThread().getContextClassLoader());
        URL deleteDemo1 = Thread.currentThread().getContextClassLoader().
                getResource("deletedemo.jar");
        MyClassLoader child = new MyClassLoader(new URL[]{deleteDemo1},myClassLoader);
        return child;
    }

    /**
     * 当子loader依赖的包在父loader中，全盘委托会去父类去找
     */
    private static MyClassLoader childDependParent(){
        URL depend = Thread.currentThread().getContextClassLoader().
                getResource("dependDemo.jar");
        URL deleteDemo1 = Thread.currentThread().getContextClassLoader().
                getResource("deletedemo.jar");
        //父loader是 appclassloader
        MyClassLoader myClassLoader = new MyClassLoader(new URL[]{deleteDemo1},
                Thread.currentThread().getContextClassLoader());
        MyClassLoader child = new MyClassLoader(new URL[]{depend},myClassLoader);
        return child;
    }
}
