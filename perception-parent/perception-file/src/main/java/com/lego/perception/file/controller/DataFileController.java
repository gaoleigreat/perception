package com.lego.perception.file.controller;


import com.lego.framework.core.annotation.Operation;
import com.lego.framework.core.page.Page;
import com.lego.framework.core.page.PagedResult;
import com.lego.framework.core.utils.UuidUtils;
import com.lego.framework.core.vo.RespDataVO;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Api(value = "FileController", description = "规则和不规则文件上传")
@RequestMapping("/datefile/v1")
@Slf4j
public class DataFileController {



    @ApiOperation(value = "testUpLoad", notes = "testUpLoad")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "多个文件，", paramType = "formData", allowMultiple = true, required = true, dataType = "file")
    })
    @PostMapping(value = "/testUpLoad")
    @Operation(value = "testUpLoad", desc = "test多文件上传")
    public RespVO testUpLoad(@RequestParam(value = "files", required = true) MultipartFile[] files) {
        return RespVOBuilder.success(files.length);
    }

    @ApiOperation(value = "upload", notes = "testOneUpLoad")
    @ApiImplicitParams({})
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    @Operation(value = "upload", desc = "upload")
    public RespVO testOneUpLoad(@RequestParam(value = "file", required = true) MultipartFile file) {
        return RespVOBuilder.success(file.getSize());
    }
}
