package com.lego.framework.system.feign;
import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.system.model.entity.Sitemap;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/18 14:46
 * @desc :
 */
@FeignClient(value = "system-service", path = "/sitemap", fallbackFactory = SitemapClientFallbackFactory.class)
public interface SitemapClient {


    /**
     * 新增菜单
     *
     * @param sitemap
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO insert(@RequestBody Sitemap sitemap);


    /**
     * 查询菜单信息
     *
     * @param sitemap
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    List<Sitemap> list(@RequestBody Sitemap sitemap);


}

@Slf4j
@Component
class SitemapClientFallbackFactory implements FallbackFactory<SitemapClient> {


    @Override
    public SitemapClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return new SitemapClient() {
            @Override
            public RespVO insert(Sitemap sitemap) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
            }

            @Override
            public List<Sitemap> list(Sitemap sitemap) {
                return null;
            }
        };
    }
}