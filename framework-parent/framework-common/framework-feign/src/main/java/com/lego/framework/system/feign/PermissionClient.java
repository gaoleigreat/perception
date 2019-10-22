package com.lego.framework.system.feign;

import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.system.model.entity.Permission;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/23
 **/
@FeignClient(value = "system-service", path = "/permission/v1", fallbackFactory = PermissionClientFallbackFactory.class)
public interface PermissionClient {


    /**
     * 添加权限点
     *
     * @param scope
     * @param permissions
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    RespVO save(@RequestParam(value = "scope") String scope, @RequestBody List<Permission> permissions);


    /**
     * @param userId
     * @return
     */
    @RequestMapping(value = "/findUserPermissions", method = RequestMethod.GET)
    List<Permission> findUserPermissions(@RequestParam(value = "userId") Long userId);

}

@Slf4j
@Component
class PermissionClientFallbackFactory implements FallbackFactory<PermissionClient> {


    @Override
    public PermissionClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return new PermissionClient() {
            @Override
            public RespVO save(String scope, List<Permission> permissions) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
            }

            @Override
            public List<Permission> findUserPermissions(Long userId) {
                return null;
            }
        };
    }
}
