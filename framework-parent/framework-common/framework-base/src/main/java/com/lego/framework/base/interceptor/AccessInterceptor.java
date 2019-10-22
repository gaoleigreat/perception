package com.lego.framework.base.interceptor;


import com.lego.framework.core.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yanglf
 * @description 日志拦截器
 * @since 2018/12/22
 **/
@Slf4j(topic = "access")
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            log.info(String.format("Request from -> IP: %s, Method: %s, URL: %s, Params: %s, Headers: %s",
                    HttpUtils.getClientIp(request),
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    HttpUtils.getRequestParams(request),
                    HttpUtils.getHeaderVo(request)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
