package com.newtonk.aspect;

import com.newtonk.exception.MyException;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by newtonk on 2017/7/10.
 */
@Aspect
@Component
public class MyAspect {

    /* 定义切点 */
    @Pointcut("execution(* com.newtonk.controller.MyController.*(..))")
    public void getController(){}

    /*增强处理,使用args来访问切点的同名参数 ,限制匹配至少一个参数的方法，注意参数位置和名字一定要对的上才能获取到*/
    @Before("getController() && args(name,..)")
    public void beforeAdvice(String name){
        System.out.println("执行MyController类的first() 方法前增强， 方法的参数name："+ name);
    }

    /* returning的返回值必须和形参一样，用于获取返回值 */
    @AfterReturning(
            pointcut="getController()",
            returning="retVal")
    public void doAccessCheck(Object retVal) {
        System.out.println("方法正常返回。判断返回值是否预期" + retVal.equals(""));
    }

    @AfterThrowing(
            pointcut="getController()",
            throwing="ex")
    public void doRecoveryActions(MyException ex) {
        System.out.println("方法抛异常后进入" + (ex instanceof RuntimeException));
    }


}
