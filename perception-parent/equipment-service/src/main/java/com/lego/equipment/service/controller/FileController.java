package com.lego.equipment.service.controller;

import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.file.feign.FileClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther xiaodao
 * @date 2019/10/17 17:29
 */
@RestController
@RequestMapping("file")
@Api(value = "文件上传", tags = "文件上传")
@Slf4j
public class FileController {
    @Autowired
    private FileClient fileClient;

    @ApiOperation(value = "多文件上传", notes = "多文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id，", paramType = "query",  required = true, dataType = "Long")
    })
    @PostMapping(value = "/uploads", headers = "content-type=multipart/form-data")
    public RespVO uploads(@RequestParam(value = "files", required = true) MultipartFile[] files, @RequestParam(value = "id", required = false) Long id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String path = "";
        Arrays.stream(files).forEach(mf -> {
            log.error(mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1));
            log.error("id :{}", id);
        });
        return fileClient.testUpLoad(files);
    }

    @ApiOperation(value = "单个文件上传", notes = "单个文件上传")
    @ApiImplicitParams({})
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public RespVO upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        return fileClient.testOneUpLoad(file);
    }
}
