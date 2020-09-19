package com.example.event;

import com.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 事件发布者
 * @Author: HYX
 * @Date: 2020/7/15 9:35
 */
@Service
public class ThingPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    public void pubEvent(Student student)
    {
        applicationContext.publishEvent(new ThingEvent(this,student));
    }


}
