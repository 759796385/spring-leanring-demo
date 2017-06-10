package com.newtonk.annotation.best;

/**
 * Created by newtonk on 2016/11/19.
 */
public class Max implements Exam{

    /**
     *  只有传入a开头才执行
     *  参数变为 param+name
     */
    @Method(param= "方法附加值. 真正参数：")
    @Override
    public String answer(@Param(onlyStart = "a") String name) {
        System.out.println("输出目标:" + name);
        return name;
    }
}
