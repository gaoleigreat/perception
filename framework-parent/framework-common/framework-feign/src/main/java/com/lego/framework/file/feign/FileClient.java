package com.lego.framework.file.feign;


import com.lego.framework.config.MultipartSupportConfig;
import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author gaolei
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "file-service", path = "/file", fallbackFactory = FileClientFallbackFactory.class,configuration = MultipartSupportConfig.class)
public interface FileClient {

    @RequestMapping(method = RequestMethod.POST , value = "/upLoads", headers = "content-type=multipart/form-data",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RespVO testUpLoad(@RequestPart(value = "files", required = true) MultipartFile[] files);

    @RequestMapping(method = RequestMethod.POST ,value = "/upLoad", headers = "content-type=multipart/form-data",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RespVO  testOneUpLoad(@RequestPart(value = "file", required = true) MultipartFile file);
}

@Slf4j
@Component
class FileClientFallbackFactory implements FallbackFactory<FileClient> {

    @Override
    public FileClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return new FileClient() {

            @Override
            public RespVO testUpLoad(MultipartFile[] files) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
            public RespVO testOneUpLoad(MultipartFile file) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }
        };
    }
}



