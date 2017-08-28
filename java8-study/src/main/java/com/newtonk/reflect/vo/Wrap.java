package com.newtonk.reflect.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by newtonk on 2017/7/23.
 */
@Getter@Setter
public class Wrap {
    private String a;
    private String b;
    static{
        System.out.println("Wrap类加载时初始化");
    }
    {
        System.out.println("Wrap实例构造时初始化");
    }

    public class DefaultInner{
        String a ;
        String b;
        {
            System.out.println("DefaultInner普通内部类是没有静态加载器的");
        }
        public void print(){
            System.out.println("DefaultInner 的方法");
        }
    }

    public static class StaticInner{
        String a;
        String b;
        static{
            System.out.println("StaticInner 静态内部类加载");
        }
        {
            System.out.println("StaticInner 实例构造");
        }
    }
}
