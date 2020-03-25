package com.lh.daily.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 全局处理异常
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    //获取日志
    private final Logger logger =  LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)//表示方法可以进行异常处理
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //记录异常信息
        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);
        //找找存在与否改变状态的注解，存在不为空
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        //打印异常信息
        ModelAndView mav = new ModelAndView();
        mav.addObject("url",request.getRequestURL());
        mav.addObject("exception",e);
        //出现异常后返回页面
        mav.setViewName("error/error");
        return mav;
    }
}
