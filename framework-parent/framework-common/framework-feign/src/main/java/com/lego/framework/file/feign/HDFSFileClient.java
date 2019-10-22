package com.lego.framework.file.feign;


import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author gaolei
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "file-service", path = "/hdfs/v1", fallbackFactory = HDFSFileClientFallbackFactory.class)
public interface HDFSFileClient {

    @PostMapping(value = "/uploadFile", headers = "content-type=multipart/form-data")
    RespVO<Map<String, String>> uploads(@RequestParam(value = "storePath") String storePath,
                                        @RequestParam(value = "savePath") String savePath,
                                        @RequestParam(value = "files", required = false) MultipartFile[] files);


}

@Slf4j
@Component
class HDFSFileClientFallbackFactory implements FallbackFactory<HDFSFileClient> {

    @Override
    public HDFSFileClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return (storePath, savePath, files) -> RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
    }
}



