package com.example.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 事件监听者
 * @Author: HYX
 * @Date: 2020/7/15 9:34
 */
@Component
public class ThingListener_a {

    @EventListener
    public void MsgListener(ThingEvent thingEvent)
    {
        String name = thingEvent.getStudent().getName();
        int age = thingEvent.getStudent().getAge();
        System.out.println("aaaa监听者a线程："+Thread.currentThread().getName());
        System.out.println("aaaa监听到消息:名称="+name+",年龄="+age);
    }
}
