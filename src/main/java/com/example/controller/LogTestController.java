package com.example.controller;

import com.example.dao.TestLogDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @Author: HYX
 * @Date: 2020/10/26 14:54
 */
@Slf4j
@RestController
@RequestMapping(value = "logTest")
@Api(value = "logTest",tags = "测试日志打印功能")
public class LogTestController {

    @Autowired
    private TestLogDao testLogDao;

    @RequestMapping(value = "insert")
    @ApiOperation(value = "新增记录",httpMethod = "POST", notes = "nots新增")
    public String insert()
    {
        String logType = "logType";
        String logUrl = "logUrl";
        String logIp = "logIp";
        String logDz = "logDz";
        int i = testLogDao.insert(logType,logUrl,logIp,logDz);
        if (i > 0)
        {
            return "success";
        }else
        {
            return "fial";
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        Unsafe unsafe = Unsafe.getUnsafe();
//        long l = unsafe.allocateMemory(1024);
//        long l1 = unsafe.reallocateMemory(1024, 1024);
//        unsafe.freeMemory(1024);
        Class<?> aClass = Class.forName("com.example.entity.Student");
        Field[] fields = aClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i]);
        }
        while (true){}
    }

    public static String print(String name,int age)
    {
        String result = "success";
        String myname = name;
        int num = 2;
        int myAge = num+age;
        System.out.println("我叫："+myname+",今年"+myAge+"岁了");
        return result;
    }

    public static void logOut()
    {
        log.trace("logOut->trace");
        log.debug("logOut->debug");
        log.info("logOut->info");
        log.warn("logOut->warn");
        log.error("logOut->error");
    }
}
