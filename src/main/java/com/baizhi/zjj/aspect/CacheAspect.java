package com.baizhi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheAspect {
    @Autowired
    RedisTemplate redisTemplate;
    @Around(value = "@annotation(com.baizhi.annotation.AddOrSelectCache)")
    public Object addOrSelectCache(ProceedingJoinPoint proceedingJoinPoint){
        // 问题: 缓存的数据结构如何设计?
        // Key : 原始类的 类名的全限定名  key: 方法名+参数 value: 数据
        String clazz = proceedingJoinPoint.getTarget().getClass().toString();
        System.out.println(clazz);
        String name = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        String key = name;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            key += arg;
        }
        // 查询数据库之前 先查询Redis中的缓存数据
        Object o = redisTemplate.opsForHash().get(clazz, key);
        // 如果查询到直接返回缓存中的数据
        if (o!=null){
            return o;
        }
        // 如果查询不到 数据库查询 将数据添加至缓存中
        try {
            // proceed() 返回值为 切入方法的返回值
            Object proceed = proceedingJoinPoint.proceed();
            redisTemplate.opsForHash().put(clazz,key,proceed);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
    @Around(value = "@annotation(com.baizhi.annotation.ClearCache)")
    public Object clearCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String s = proceedingJoinPoint.getTarget().getClass().toString();
        redisTemplate.delete(s);
        return proceedingJoinPoint.proceed();
    }
}
