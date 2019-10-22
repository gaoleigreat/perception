package com.lego.framework.base.service;

import com.lego.framework.core.vo.CurrentVo;

public interface IAuthCheckService {

    /**
     * 获取缓存数据
     * @param token
     * @return
     */
    CurrentVo getData(String token, String deviceType);

}
