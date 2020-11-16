package com.example.springAOP;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 切点设置为拦截所有标注PermissionsAnnotation的方法，截取到接口的参数，进行简单的权限校验
 * @Author: HYX
 * @Date: 2020/11/2 19:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionsAnnotation {

}
