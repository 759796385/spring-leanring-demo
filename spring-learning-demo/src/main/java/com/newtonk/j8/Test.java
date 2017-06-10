package com.newtonk.j8;

import com.newtonk.j8.interfaces.FunctionInterfaceTest;
import com.newtonk.j8.interfaces.InterfaceTest;
import com.newtonk.j8.interfaces.methodAndConstructorRef.Person;
import com.newtonk.j8.interfaces.methodAndConstructorRef.PersonFactory;
import com.newtonk.j8.interfaces.methodAndConstructorRef.Something;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public class Test {

    public static void main(String[] args) {
        FunctionInterfaceTest简化版();

    }

    void  InterfaceTest(){
        InterfaceTest interfaceTest = new InterfaceTest(){
            @Override
            public double abstractMethod(int a, int b) {
                return a+b;
            }

        };
        System.out.println(interfaceTest.add(5,2));
        System.out.println(interfaceTest.abstractMethod(5,3));
    }


    static void FunctionInterfaceTest(){
        //如果@FunctionalInterface注解被省略，lambda表达式编译时依然会检查这个接口只有一个抽象方法。
        FunctionInterfaceTest<String,Integer> covert = (from -> Integer.valueOf(from) );
        Integer a = covert.converter("123");
        System.out.println(a);
    }

    static void FunctionInterfaceTest简化版(){
        FunctionInterfaceTest<String,Integer> covert = Integer::valueOf;
        Integer a = covert.converter("123");
        System.out.println(a);

        //java8 可以直接使用方法名  或者::来调用方法名
        Something something = new Something();
        FunctionInterfaceTest<String,String> covertb = something::startWith;
        String converted = covertb.converter("Java");
        System.out.println(converted);    // "J"

        PersonFactory<Person> personFactory = Person::new; //用lambda表达式来生成 这里引用用Person的无参构造器来生成personFactory的create方法实现。
        Person person = personFactory.create("123","321");
        System.out.println(person.firstName);
    }


}
