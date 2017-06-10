package com.newtonk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by newtonk on 2016/11/19.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Try {
    public enum Color{RED,BLUE,YELLOW,NONE}

    public String color() default "NONE";
}
