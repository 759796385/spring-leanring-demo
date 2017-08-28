package com.newtonk.reflect;

import com.newtonk.reflect.vo.Wrap;

import java.lang.reflect.Method;

/**
 * Created by newtonk on 2017/7/23.
 */
public class InnerClass {
    public static void main(String[] args) throws Exception{
        Class wrapClazz = Class.forName("com.newtonk.reflect.vo.Wrap");
        System.out.println("M此时以触发Wrap类加载，执行静态构造器");
        Wrap wrap = (Wrap) wrapClazz.newInstance();
        System.out.println("M构造wrap实例，触发普通构造器");
        wrap.setA("a");

        System.out.println("---------我是分割线---------");
        /* 成员内部类 */
        Class defaultInnerClazz = Class.forName("com.newtonk.reflect.vo.Wrap$DefaultInner");
        Method print = defaultInnerClazz.getDeclaredMethod("print");
        Wrap.DefaultInner defaultInner = (Wrap.DefaultInner) defaultInnerClazz.getDeclaredConstructors()[0].newInstance(wrap);
        /* 调用声明方法，需要通过内部类的构造器来实例对象 */
        //        print.invoke(defaultInnerClazz.getDeclaredConstructors()[0].newInstance(wrap));
        System.out.println("M加载成员内部类");

        System.out.println("------ 静态内部类分割线线-------");
        Class staticInnerClazz = Class.forName("com.newtonk.reflect.vo.Wrap$StaticInner");
        System.out.println("M StaticInner类加载器加载类。执行静态代码块");
        Wrap.StaticInner staticInner = (Wrap.StaticInner) staticInnerClazz.getDeclaredConstructors()[0].newInstance();
        System.out.println(staticInner.getClass().getClassLoader());
        /*反射时内部类一定要声明为public*/

    }
}
