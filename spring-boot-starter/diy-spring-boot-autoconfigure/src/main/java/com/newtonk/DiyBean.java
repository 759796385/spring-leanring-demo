package com.newtonk;

/**
 * 类名称：
 * 类描述：
 * 创建人：newtonk
 * 创建日期：2019/11/17
 */
public class DiyBean {


    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void dosome() {
        if (age == null) {
            System.out.println("18");
        } else {
            System.out.println("强哥最帅，年龄" + age);
        }
    }

}
