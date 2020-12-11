package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * test_log
 * @author 
 */
@Data
public class TestLog implements Serializable {
    private Integer id;

    private String logtype;

    private String logurl;

    private String logip;

    private String logdz;

    private String ladduser;

    private String lfadduser;

    private Date laddtime;

    private String htmlname;

    private static final long serialVersionUID = 1L;
}