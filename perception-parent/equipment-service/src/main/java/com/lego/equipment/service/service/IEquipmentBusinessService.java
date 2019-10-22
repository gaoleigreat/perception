package com.lego.equipment.service.service;

import com.lego.framework.core.page.Page;
import com.lego.framework.core.page.PagedResult;
import com.lego.framework.equipment.model.entity.EquipmentBusiness;

import java.util.List;

/**
 * service层
 *
 * @author itar
 * @email wuhandzy@gmail.com
 * @date 2019-09-24 10:07:49
 * @since jdk 1.8
 */
public interface IEquipmentBusinessService {


    PagedResult<EquipmentBusiness> selectPaged(EquipmentBusiness equipmentBusiness, Page page);

    EquipmentBusiness selectByPrimaryKey(Long id);

    List<EquipmentBusiness> selectByEquipmentid(Long equipmentId);

    Integer deleteByPrimaryKey(Long id);

    Integer insert(EquipmentBusiness equipmentBusiness);

    Integer insertSelective(EquipmentBusiness equipmentBusiness);

    Integer insertSelectiveIgnore(EquipmentBusiness equipmentBusiness);

    Integer updateByPrimaryKeySelective(EquipmentBusiness equipmentBusiness);

    Integer updateByPrimaryKey(EquipmentBusiness equipmentBusiness);

    Integer batchInsert(List<EquipmentBusiness> list);

    Integer batchUpdate(List<EquipmentBusiness> list);

    /**
     * 存在即更新
     *
     * @return
     */
    Integer upsert(EquipmentBusiness equipmentBusiness);

    /**
     * 存在即更新，可选择具体属性
     *
     * @return
     */
    Integer upsertSelective(EquipmentBusiness equipmentBusiness);

    List<EquipmentBusiness> query(EquipmentBusiness equipmentBusiness);

    Long queryTotal();

    Integer deleteBatch(List<Long> list);

}
