package com.newtonk.stream.main;

import com.newtonk.stream.Vo.Person;

/**
 * Created by newtonk on 2017/5/22.
 */
public class SteamTest {
    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
//        Person person1 = new Person();
        Person person = Person.builder().age("sa").isDel(true).name("sa").build();
        System.out.println(person.toString());
//        String age = person.getAge();
//        person.
    }
}
