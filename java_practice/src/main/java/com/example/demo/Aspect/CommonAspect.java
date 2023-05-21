package com.example.demo.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class CommonAspect {

    @Before("execution(* com.example.demo.Controller.*.*(..))")
    public void before() {
        log.info("OKです");
    }

    @After("execution(* com.example.demo.Controller.*.*(..))")
    public void after() {
        log.info("終わりです");
    }
}
