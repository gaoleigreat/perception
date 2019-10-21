package com.lego.framework.eureka.config;

import com.netflix.discovery.BackupRegistry;
import com.netflix.discovery.shared.Applications;

/**
 * @author yanglf
 * @description    全部服务不可用时  加载默认的地址
 * @since 2019/7/15
 **/

public class BackupServerRegistry implements BackupRegistry {

    private Applications loadRegionApps = new Applications();



    @Override
    public Applications fetchRegistry() {
        return loadRegionApps;
    }

    @Override
    public Applications fetchRegistry(String[] strings) {
        return null;
    }
}
