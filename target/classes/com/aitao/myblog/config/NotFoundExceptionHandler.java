package com.aitao.myblog.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author : AiTao
 * Date : 2020/10/18
 * Time : 17:55
 * Information : 404异常处理
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExceptionHandler extends RuntimeException{
    public NotFoundExceptionHandler(){

    }

    public NotFoundExceptionHandler(String message) {
        super(message);
    }

    public NotFoundExceptionHandler(String message, Throwable throwable) {
        super(message,throwable);
    }
}
