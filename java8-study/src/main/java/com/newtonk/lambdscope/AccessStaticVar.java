package com.newtonk.lambdscope;

import com.newtonk.interfaces.FunctionInterfaceTest;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public class AccessStaticVar {
    static int staticNum;
    int Num;

    public static void main(String[] args) {
    }

    public void test(){
        //匿名函数对外部的成员变量和静态变量都有访问权限
        FunctionInterfaceTest<String,Integer> converter = from -> {
            Num = 2;
            return Integer.valueOf( from);
        };

        FunctionInterfaceTest<String,Integer> converterB = from -> {
            Num = 3;
            return Integer.valueOf( from);
        };
    }


}
