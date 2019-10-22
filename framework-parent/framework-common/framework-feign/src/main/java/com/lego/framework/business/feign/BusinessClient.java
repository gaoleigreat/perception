package com.lego.framework.business.feign;

import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.business.model.entity.Business;
import com.lego.framework.template.model.entity.FormTemplate;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/10 10:19
 * @desc :
 */
@FeignClient(value = "business-service", path = "/business", fallbackFactory = BusinessClientFallbackFactory.class)
public interface BusinessClient {

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    RespVO<Business> selectById(@RequestParam(value = "id") Long id);

    /**
     * @param formTemplate
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    RespVO createBusiness(@RequestBody FormTemplate formTemplate,
                          @RequestParam(value = "sourceType") Integer sourceType);


    /**
     * 新增業務數據
     *
     * @param formTemplate
     * @param fileId
     * @param data
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    RespVO insertBusinessData(@RequestBody FormTemplate formTemplate,
                              @RequestParam(value = "fileId") Long fileId,
                              @RequestParam(value = "data") List<Map<String, Object>> data,
                              @RequestParam(value = "sourceType") Integer sourceType);


    /**
     * 查詢業務員數據
     *
     * @param formTemplate
     * @param data
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    RespVO queryBusinessData(@RequestBody FormTemplate formTemplate,
                             @RequestParam(value = "data") Map<String, Object> data,
                             @RequestParam(value = "sourceType") Integer sourceType);


    /**
     * @param formTemplate
     * @param data
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    RespVO updateBusinessData(@RequestBody FormTemplate formTemplate,
                              @RequestParam(value = "data") Map<String, Object> data,
                              @RequestParam(value = "sourceType") Integer sourceType);


    /**
     * @param formTemplate
     * @param data
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    RespVO delBusinessData(@RequestBody FormTemplate formTemplate,
                           @RequestParam(value = "data") Map<String, Object> data,
                           @RequestParam(value = "sourceType") Integer sourceType);


}


@Slf4j
@Component
class BusinessClientFallbackFactory implements FallbackFactory<BusinessClient> {
    @Override
    public BusinessClient create(Throwable throwable) {
        log.error("fallback; reason was:{}", throwable);
        return new BusinessClient() {
            @Override
            public RespVO<Business> selectById(Long id) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO createBusiness(FormTemplate formTemplate, Integer sourceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO insertBusinessData(FormTemplate formTemplate, Long fileId, List<Map<String, Object>> data, Integer sourceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO queryBusinessData(FormTemplate formTemplate, Map<String, Object> data, Integer sourceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO updateBusinessData(FormTemplate formTemplate, Map<String, Object> data, Integer sourceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }

            @Override
            public RespVO delBusinessData(FormTemplate formTemplate, Map<String, Object> data, Integer sourceType) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "business服务不可用");
            }
        };
    }
}
