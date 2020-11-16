package com.example.springAOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP切面类-LOG
 * 注解Aspect:描述一个切面类
 * 注解Component：将该类交给 Spring 来管理
 * @Author: HYX
 * @Date: 2020/11/2 17:23
 */
@Aspect
@Component
public class LogAdvice {

    /**
     * 定义一个切点，所有被GetMapping注解修饰的方法会织入advice
     * Pointcut注解指定一个切面，定义需要拦截的东西，这里介绍两个常用的表达式：一个是使用 execution()，另一个是使用 annotation()。
     */
    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void logAdvicePointCut() {}

    @Before(value = "logAdvicePointCut()")
    public void logAdvice()
    {
        System.out.println("打印日志！");
    }





}
