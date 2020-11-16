package com.example.springAOP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 权限校验切面类   当被自定义注解（@PermissionsAnnotation）标识的方法执行时，打印参数信息
 * @Author: HYX
 * @Date: 2020/11/2 19:57
 */
@Aspect
@Component
@Order(1)
public class PermissionFirstAdvice {

    /**
     * 定义一个切面
     * Pointcut：用来定义一个切面，即上文中所关注的某件事情的入口，切入点定义了事件触发时机。
     * 常用的表达式：一个是使用 execution()，另一个是使用 annotation()。
     *  以 execution(* com.mutest.controller..*.*(..))) 表达式为例：
     *  第一个 * 号的位置：表示返回值类型，* 表示所有类型。
     *  包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，在本例中指 com.mutest.controller包、子包下所有类的方法。
     *  第二个 * 号的位置：表示类名，* 表示所有类。
     *  *(..)：这个星号表示方法名，* 表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     *  annotation() 表达式：
     *  annotation() 方式是针对某个注解来定义切面，比如我们对具有 @PostMapping 注解的方法做切面，可以如下定义切面：
     */
    @Pointcut(value = "@annotation(com.example.springAOP.PermissionsAnnotation)")
    public void permissionCheck(){}

    @Around("permissionCheck()")
    public Object permissionCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long id = ((JSONObject) args[0]).getLong("id");
        String name = ((JSONObject) args[0]).getString("id");
        System.out.println("id:"+id);
        System.out.println("name:"+name);
        if (id < 0)
        {
            return JSON.parse("id 不能小于0");
        }
        return joinPoint.proceed();
    }


}
