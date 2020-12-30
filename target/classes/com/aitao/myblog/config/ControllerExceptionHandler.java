package com.aitao.myblog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Author : AiTao
 * Date : 2020/10/18
 * Time : 17:32
 * Information : 所有异常处理（除404）
 */

//拦截所有标注 @Controller 注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截所有 Exception 异常的处理方法
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,
                                         Exception e) throws Exception {
        logger.error("Request URL:{},Exception:{}", request.getRequestURL(), e);
        //不拦截 @ResponseStatus 注解标注的异常类
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("500");
        return modelAndView;
    }
}
