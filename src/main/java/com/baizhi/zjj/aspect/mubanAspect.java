package com.baizhi.zjj.aspect;/*
package com.baizhi.zjj.aspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


//@aspect  //代表切面
//@Pointcut  //代表切入点
//@Before()   //前置通知
//@After()    //后置通知
//@Around()   //环绕通知
//@AfterThrowing //异常通知

@Component
@Slf4j //日志
@Aspect
public class MyAspect {
//    @Pointcut(value = "execution(* com.baizhi.zjj.service.*.*(..))")
    @Pointcut(value = "@annotation(com.baizhi.zjj.aspect.LogAnnotation)")
    public void pt(){
        //空方法
    }

    @Around(value = "pt()")
    public Object cc(ProceedingJoinPoint proceedingJoinPoint){
        log.info("---环绕通知开始：方法执行之前---");

        Object proceed =null;
        try {
            //原始方法的返回值
            proceed = proceedingJoinPoint.proceed();
            log.info("this is  result=====>{}",proceed);
            //必须返回 否则controller拿不到结果
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            log.info("---环绕通知结束：方法执行之后---");
        }
        return null;

    }
}
*/
