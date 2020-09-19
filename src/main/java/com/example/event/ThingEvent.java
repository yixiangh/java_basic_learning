package com.example.event;

import com.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

/**
 * 事件
 * @Author: HYX
 * @Date: 2020/7/15 9:30
 */
public class ThingEvent extends ApplicationEvent {

    @Autowired
    private Student student;
    /**
     * Create a new {@code ApplicationEvent}.
     * @param source the object on which the event initially occurred or with
     * which the event is associated (never {@code null})
     */
    public ThingEvent(Object source,Student student) {
        super(source);
        this.student = student;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
