package com.lego.framework.business.feign;

import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.page.PagedResult;
import com.lego.framework.core.vo.RespDataVO;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.template.model.entity.SearchParam;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/25 19:43
 * @desc :
 */
@FeignClient(value = "business-service", path = "/crud", fallbackFactory = CrudClientFallbackFactory.class)
public interface CrudClient {

    /**
     * 创建业务表
     *
     * @param templateCode
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    RespVO createBusiness(@RequestParam(value = "templateCode") String templateCode);


    /**
     * 新增业务数据
     *
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO insertBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestBody List<Map<String, Object>> data);


    /**
     * 查询业务数据
     *
     * @param templateCode
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(@RequestParam(value = "templateCode") String templateCode,
                                                              @RequestBody List<SearchParam> searchParams);

    /**
     * @param templateCode
     * @param equipmentCode
     * @return
     */
    @RequestMapping(value = "/queryByCode", method = RequestMethod.GET)
    RespVO<Map<String, Object>> queryBusinessDataByCode(@RequestParam(value = "templateCode") String templateCode,
                                                        @RequestParam(value = "equipmentCode") String equipmentCode);


    /**
     * 分页查询业务数据
     *
     * @param templateCode
     * @param searchParams
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/queryDataPaged/{pageSize}/{pageIndex}", method = RequestMethod.POST)
    RespVO<PagedResult<Map<String, Object>>> queryDataPaged(@RequestParam(value = "templateCode") String templateCode,
                                                            @RequestBody List<SearchParam> searchParams,
                                                            @PathVariable(value = "pageSize") Integer pageSize,
                                                            @PathVariable(value = "pageIndex") Integer pageIndex);


    /**
     * 更新业务数据
     *
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    RespVO updateBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestBody Map<String, Object> data);


    /**
     * 删除业务数据
     *
     * @param templateCode
     * @param data
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    RespVO delBusinessData(@RequestParam(value = "templateCode") String templateCode,
                           @RequestBody Map<String, Object> data);


    /**
     * 上传业务数据
     *
     * @param templateCode
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    RespVO uploadBusinessData(@RequestParam(value = "templateCode") String templateCode,
                              @RequestParam(value = "file") MultipartFile file);

}

@Slf4j
@Component
class CrudClientFallbackFactory implements FallbackFactory<CrudClient> {

    @Override
    public CrudClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return new CrudClient() {
            @Override
            public RespVO createBusiness(String templateCode) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO insertBusinessData(String templateCode, List<Map<String, Object>> data) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO<RespDataVO<Map<String, Object>>> queryBusinessData(String templateCode, List<SearchParam> searchParams) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO<Map<String, Object>> queryBusinessDataByCode(String templateCode, String equipmentCode) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO<PagedResult<Map<String, Object>>> queryDataPaged(String templateCode, List<SearchParam> searchParams, Integer pageSize, Integer pageIndex) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO updateBusinessData(String templateCode, Map<String, Object> data) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO delBusinessData(String templateCode, Map<String, Object> data) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO uploadBusinessData(String templateCode, MultipartFile file) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }
        };
    }
}
