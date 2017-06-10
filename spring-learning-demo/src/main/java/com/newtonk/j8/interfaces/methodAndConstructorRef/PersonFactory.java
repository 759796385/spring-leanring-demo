package com.newtonk.j8.interfaces.methodAndConstructorRef;

/**
 * 接口名称：
 * 接口描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
