package com.lego.framework.core.utils;

import com.lego.framework.core.consts.HttpConsts;
import com.lego.framework.core.vo.HeaderVo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanglf
 * @description
 * @since 2018/12/27
 **/
@Slf4j
public class HeaderUtils {

    public static HeaderVo parseHeader(HttpServletRequest request) {
        try {
            String token = request.getHeader(HttpConsts.HEADER_TOKEN);
            String fromName = request.getHeader(HttpConsts.FROM_NAME);
            String userId = request.getHeader(HttpConsts.USER_ID);
            String userName = request.getHeader(HttpConsts.USER_NAME);
            return HeaderVo.builder()
                    .token(token)
                    .userId(userId)
                    .userName(userName)
                    .fromName(fromName)
                    .build();
        } catch (Exception ex) {
            log.error("header参数解析失败:{}",ex);
            return null;
        }
    }





}
