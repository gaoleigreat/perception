package com.lego.framework.file.feign;


import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespDataVO;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.config.MultipartSupportConfig;
import com.lego.framework.system.model.entity.DataFile;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gaolei
 * @description
 * @since 2019/8/27
 **/
@FeignClient(value = "file-service", path = "/datefile/v1", fallbackFactory = FileClientFallbackFactory.class,configuration = MultipartSupportConfig.class)
public interface FileClient {

    @PostMapping(value = "/uploads", headers = "content-type=multipart/form-data",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RespVO<RespDataVO<DataFile>> uploads(@RequestPart(value = "files", required = true) MultipartFile[] files,
                                         @RequestParam(value = "projectId", required = false) Long projectId,
                                         @RequestParam(value = "templateId", required = false) Long templateId,
                                         @RequestParam(value = "sourceType", required = true) int sourceType,
                                         @RequestParam(value = "remark", required = false) String remark,
                                         @RequestParam(value = "tags", required = false) String tags);

    /**
     * 通过批次号标签查询文件
     *
     * @param batchNums
     * @param tags      标签（多标签使用逗号隔开）
     * @return
     */
    @RequestMapping(value = "/selectByBatchNums", method = RequestMethod.GET)
    RespVO selectByBatchNums(@RequestParam(value = "bathNums") List<String> batchNums,
                             @RequestParam(required = false, value = "tags") String tags);


    /**
     * 上传业务文件
     *
     * @param files
     * @param remark
     * @param tags   标签（多标签使用逗号隔开）
     * @return
     */
    @PostMapping(value = "/upLoadFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RespVO<RespDataVO<DataFile>> upLoadFile(@RequestPart(value = "files") MultipartFile[] files,
                                            @RequestParam(required = false, value = "remark") String remark,
                                            @RequestParam(required = false, value = "tags") String tags);


    /**
     * @param files
     * @param remark
     * @param tags
     * @return
     */
    @PostMapping(value = "/upLoadFile1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RespVO<RespDataVO<DataFile>> upLoad(@RequestPart(value = "files") MultipartFile[] files,
                                        @RequestParam(required = false, value = "remark") String remark,
                                        @RequestParam(value = "tags") String tags,
                                        @RequestParam(required = false, value = "fileId") Long fileId);



    @RequestMapping(method = RequestMethod.POST , value = "/testUpLoad", headers = "content-type=multipart/form-data",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RespVO testUpLoad(@RequestPart(value = "files", required = true) MultipartFile[] files);

    @RequestMapping(method = RequestMethod.POST ,value = "/testOneUpLoad", headers = "content-type=multipart/form-data",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
            public RespVO<RespDataVO<DataFile>> uploads(MultipartFile[] files, Long projectId, Long templateId, int sourceType, String remark, String tags) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
            public RespVO<RespDataVO<DataFile>> selectByBatchNums(List<String> bathNums, String tags) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
           public RespVO<RespDataVO<DataFile>> upLoadFile(MultipartFile[] files, String remark, String tags) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
            public RespVO<RespDataVO<DataFile>> upLoad(MultipartFile[] files, String remark, String tags, Long fileId) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
            public RespVO<RespDataVO<DataFile>> testUpLoad(MultipartFile[] files) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }

            @Override
            public RespVO<RespDataVO<DataFile>> testOneUpLoad(MultipartFile file) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "file服务不可用");
            }
        };
    }
}



