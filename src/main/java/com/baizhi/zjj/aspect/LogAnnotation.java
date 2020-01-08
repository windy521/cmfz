package com.baizhi.zjj.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解  只适用于 代理方式为cglib的情况
//该注解为自己写的注解(自定义注解)

// 运行时机：RUNTIME
@Retention(RetentionPolicy.RUNTIME)
//当前注解作用在类上 方法上 还是属性上
@Target({ElementType.METHOD})
public @interface LogAnnotation {
    String value();
}
