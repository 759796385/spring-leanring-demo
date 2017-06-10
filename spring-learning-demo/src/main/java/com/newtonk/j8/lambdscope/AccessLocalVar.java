package com.newtonk.j8.lambdscope;

import com.newtonk.j8.interfaces.FunctionInterfaceTest;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public class AccessLocalVar {
    public static void main(String[] args) {
         int num =1 ;
        //在匿名函数内部可访问外部final变量。 注意：num必须显示声明final，如果不显示声明，也能编译通过，但在后面修改了值，会报错
        FunctionInterfaceTest<Integer,String> stringConvert = (from) -> String.valueOf(from + num) ;
//        num =3 ; 加上此段就会编译报错
        System.out.println(stringConvert.converter(23));
    }
}
