package com.lego.framework.system.feign;

import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespDataVO;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.system.model.entity.DataFile;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author yanglf
 * @description
 * @since 2019/8/23
 **/
@FeignClient(value = "system-service", path = "/dataFile/v1", fallbackFactory = DataFileClientFallbackFactory.class)
public interface DataFileClient {


    /**
     * 添加文件
     *
     * @param dataFiles
     * @return
     */
    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    RespVO<RespDataVO<Long>> insertList(@RequestBody List<DataFile> dataFiles);

    /**
     * 添加文件
     *
     * @param dataFile
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO<Long> insert(@RequestBody DataFile dataFile);
}

@Component
@Slf4j
class DataFileClientFallbackFactory implements FallbackFactory<DataFileClient> {

    @Override
    public DataFileClient create(Throwable throwable) {
        log.error("fallback; reason was:", throwable);
        return new DataFileClient() {
            @Override
            public RespVO<RespDataVO<Long>> insertList(List<DataFile> dataFiles) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
            }

            @Override
            public RespVO<Long> insert(DataFile dataFile) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "system服务不可用");
            }
        };
    }
}
