package com.example.dao;

import com.example.entity.TestLog;

public interface TestLogDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TestLog record);

    TestLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestLog record);

    int updateByPrimaryKey(TestLog record);

    int insert(String logType, String logUrl, String logIp, String logDz);
}