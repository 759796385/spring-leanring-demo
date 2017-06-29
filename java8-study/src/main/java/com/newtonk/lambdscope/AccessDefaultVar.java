package com.newtonk.lambdscope;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public class AccessDefaultVar {
    public static void main(String[] args) {
        //在InterfaceTest中声明的默认实现方法add在任何实例对象中都能调用，包括匿名函数，但是在lambda表达式中无法调用。
//        InterfaceTest s = (a,b) -> add(a,b);
    }
}
