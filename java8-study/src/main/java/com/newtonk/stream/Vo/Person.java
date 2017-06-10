package com.newtonk.stream.Vo;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by newtonk on 2017/5/22.
 */

@Slf4j
@Builder
@Data
public class Person implements Serializable{
    public  String name;
    private  String age;
    private boolean isDel;

//    @Tolerate
//    public Person(){}

   public void test(){
       val sa = new HashMap<String,String>();
       System.out.println(sa);
   }


//    @Override
//    public String toString() {
//        return "Person{" +
//                "name='" + name + '\'' +
//                ", age='" + age + '\'' +
//                ", isDel=" + isDel +
//                '}';
//    }
}
