package com.newtonk.j8.interfaces;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public interface InterfaceTest {
    //接口加上public abstract，编译后字节码会去掉这俩货


    double abstractMethod(int a, int b);

    //java8允许我们使用default关键字来 编写接口中非抽象方法的实现
    default double add(int a, int b){
        return a+b;
    }


}
