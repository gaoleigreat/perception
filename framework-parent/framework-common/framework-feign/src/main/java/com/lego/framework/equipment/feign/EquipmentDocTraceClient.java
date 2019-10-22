package com.lego.framework.equipment.feign;

import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.RespDataVO;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.equipment.model.entity.EquipmentDocTrace;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/10/6 14:36
 * @desc :
 */
@FeignClient(value = "equipment-service", path = "/equipmentDocTrace", fallbackFactory = EquipmentDocTraceClientFallbackFactory.class)
public interface EquipmentDocTraceClient {

    /**
     * 查询设备文档轨迹
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/select_by_id", method = RequestMethod.GET)
    RespVO<EquipmentDocTrace> selectByPrimaryKey(@RequestParam(value = "id") Long id);


    /**
     * 查询设备文档轨迹
     *
     * @param equipmentDocTrace
     * @return
     */
    @RequestMapping(value = "/query_list", method = RequestMethod.POST)
    RespVO<RespDataVO<EquipmentDocTrace>> queryByCondition(@RequestBody EquipmentDocTrace equipmentDocTrace);

}

@Component
@Slf4j
class EquipmentDocTraceClientFallbackFactory implements FallbackFactory<EquipmentDocTraceClient> {

    @Override
    public EquipmentDocTraceClient create(Throwable throwable) {
        log.error("fallback; reason was:", throwable);
        return new EquipmentDocTraceClient() {
            @Override
            public RespVO<EquipmentDocTrace> selectByPrimaryKey(Long id) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }

            @Override
            public RespVO<RespDataVO<EquipmentDocTrace>> queryByCondition(EquipmentDocTrace equipmentDocTrace) {
                return RespVOBuilder.failure(RespConsts.ERROR_SERVER_CODE, "equipment服务不可用");
            }
        };
    }
}
