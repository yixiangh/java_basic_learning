package com.example.springAOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP切面类-LOG
 * 注解Aspect:描述一个切面类
 * 注解Component：将该类交给 Spring 来管理
 * @Author: HYX
 * @Date: 2020/11/2 17:23
 */
@Aspect
@Component
@Slf4j
public class LogSecondAdvice {

    /**
     * 定义一个切点，所有被GetMapping注解修饰的方法会织入advice
     * Pointcut注解指定一个切面，定义需要拦截的东西，这里介绍两个常用的表达式：一个是使用 execution()，另一个是使用 annotation()。
     * 第一个 * 号的位置：表示返回值类型，* 表示所有类型。
     * 包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，在本例中指 com.mutest.controller包、子包下所有类的方法。
     * 第二个 * 号的位置：表示类名，* 表示所有类。
     * *(..)：这个星号表示方法名，* 表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     */
    @Pointcut(value = "execution(* com.example.springAOP..*.*(..))")
    private void logAdvicePointCut() {}

    @Before(value = "logAdvicePointCut()")
    public void logAdvice(JoinPoint joinPoint)
    {
        // 获取签名
        Signature signature = joinPoint.getSignature();
        // 获取切入的包名
        String declaringTypeName = signature.getDeclaringTypeName();
        // 获取即将执行的方法名
        String funcName = signature.getName();
        log.info("即将执行方法为: {}，属于{}包", funcName, declaringTypeName);
        Object[] args = joinPoint.getArgs();
        if (args.length > 0)
        {
            log.info("参数列表：{}",args[0]);
        }else
        {
            log.info("无参请求");
        }

        // 也可以用来记录一些信息，比如获取请求的 URL 和 IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求 URL
        String url = request.getRequestURL().toString();
        // 获取请求 IP
        String ip = request.getRemoteAddr();
        log.info("用户请求的url为：{}，ip地址为：{}", url, ip);
    }





}
