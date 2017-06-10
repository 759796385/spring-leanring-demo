package com.newtonk.j8.interfaces;

/**
 * 类名称：
 * 类描述： 使用@FunctionalInterface注解来表示这个接口只能有一个抽象方法。当有两个，编译器会报错
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
@FunctionalInterface
public interface FunctionInterfaceTest<F,T> {
    //将后面一个函数转换成前面的
    T converter(F from);

}
