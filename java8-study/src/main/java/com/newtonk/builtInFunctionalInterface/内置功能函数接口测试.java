package com.newtonk.builtInFunctionalInterface;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/30 0030
 */
public class 内置功能函数接口测试 {
    public static void main(String[] args) {
        predicatesTest();
    }

    /**
     * 就是为guava包提供的一些函数整合到lang.util包了,这个函数在集合的stream中常用，熟练掌握
     *
     */
    static void predicatesTest(){
        // Predicate 提供判断方法
        Predicate<String> predicate = (s) -> s.length() > 0; //为Predicate的test函数提供匿名实现，  boolean test(String t);
        System.out.println(predicate.test("foo"));
        System.out.println(predicate.negate().test("foo")); //取反

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println(isEmpty.test(""));
    }

    static void FunctionsTest(){
        //Function<input,result>接口提供执行方法(input)，返回result
        Function<String,Integer> string2Integer = Integer::valueOf; //为apply提供匿名实现，由 Integer的valueOf提供
        Function<String, String> backToString = string2Integer.andThen(String::valueOf);

        backToString.apply("123");
    }
}
