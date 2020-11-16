package com.example.controller;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @Author: HYX
 * @Date: 2020/10/26 14:54
 */
@Slf4j
public class LogTest {

    public static void logOut()
    {
        log.trace("logOut->trace");
        log.debug("logOut->debug");
        log.info("logOut->info");
        log.warn("logOut->warn");
        log.error("logOut->error");
    }
}
