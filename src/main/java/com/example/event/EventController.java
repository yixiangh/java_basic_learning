package com.example.event;

import com.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author: HYX
 * @Date: 2020/7/15 10:01
 */
@RestController
@RequestMapping(value = "eventPub")
public class EventController {

    @Autowired
    private ThingPublisher thingPublisher;

    @RequestMapping(value = "registerEvent")
    public String register(String sex,int age,boolean isnull)
    {
        Student stu = new Student();
        if (isnull != true)
        {
            stu.setName("张三");
            stu.setAge(age);
            stu.setSex(sex);
            stu.setId(1234);
        }
        thingPublisher.pubEvent(stu);
        System.out.println("主线程："+Thread.currentThread().getName());
        return "success";
    }
}
