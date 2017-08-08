package com.newtonk.interfaces;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public class lambdaExpresss {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        //用lambda表达式代替匿名函数
        // 原版
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });


        names.sort((a, b) -> b.compareTo(a));

        System.out.println(names);
    }
}
