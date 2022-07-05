package com.sunghyuki.demospringsecurity.common;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityLogger {

    public static void log(String message) {
        System.out.println(message);
        Thread thread = Thread.currentThread();
        System.out.println("Thread : " + thread.getName());
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Principle : " + principle);
    }
}
