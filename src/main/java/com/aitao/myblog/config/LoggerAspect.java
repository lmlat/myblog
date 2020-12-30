package com.aitao.myblog.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Author : AiTao
 * Date : 2020/10/18
 * Time : 18:47
 * Information : 通过使用 AOP 进行日志处理
 */
@Aspect
@Component
public class LoggerAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //切面方法
    @Pointcut("execution(* com.aitao.myblog.controller.*.*(..))")
    public void logger() {

    }

    //前置通知
    @Before("logger()")
    public void beforeLogger(JoinPoint joinPoint) {
        //获取HttpServletRequest对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //通过HttpServletRequest获取ip、url
        //通过JoinPoint接口中的Signature接口获取类方法名、参数列表
        RequestLoggerInfo loggerInfo = new RequestLoggerInfo(request.getRemoteAddr(), request.getRequestURL().toString(), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), joinPoint.getArgs());
        logger.info("==========Request : {}==========", loggerInfo);
    }

    //后置通知
    @AfterReturning(returning = "result", pointcut = "logger()")
    public void afterReturningLogger(Object result) {
        logger.info("==========Result : {}==========", result);
    }

    //异常通知
    @AfterThrowing("logger()")
    public void afterThrowingLogger() {
        logger.info("==========afterThrowingLogger==========");

    }

    //最终通知
    @After("logger()")
    public void afterLogger() {
        logger.info("==========afterLogger==========");

    }

    //请求参数信息
    private class RequestLoggerInfo {
        private String ip;//请求ip
        private String url;//请求url
        private String classMethod;//请求方法名的全路径
        private Object[] params;//请求参数列表

        public RequestLoggerInfo(String ip, String url, String classMethod, Object[] params) {
            this.ip = ip;
            this.url = url;
            this.classMethod = classMethod;
            this.params = params;
        }

        @Override
        public String toString() {
            return "RequestLoggerInfo{" +
                    "ip='" + ip + '\'' +
                    ", url='" + url + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", params='" + params + '\'' +
                    '}';
        }
    }
}
