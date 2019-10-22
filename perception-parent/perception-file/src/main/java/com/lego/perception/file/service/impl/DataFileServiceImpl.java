package com.lego.perception.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lego.framework.core.exception.ExceptionBuilder;
import com.lego.framework.core.page.Page;
import com.lego.framework.core.page.PagedResult;
import com.lego.framework.core.utils.UuidUtils;
import com.lego.framework.core.vo.RespDataVO;
import com.lego.framework.core.vo.RespVO;
import com.lego.framework.core.vo.RespVOBuilder;
import com.lego.framework.mybatis.utils.PageUtil;
import com.lego.framework.system.model.entity.DataFile;
import com.lego.perception.file.mapper.DataFileMapper;
import com.lego.perception.file.service.IDataFileService;
import com.lego.perception.file.service.IHdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataFileServiceImpl implements IDataFileService {

    @Autowired
    private DataFileMapper dataFileMapper;

    @Autowired
    private IHdfsService iHdfsService;

    @Value("${hdfs.storePath}")
    private String storePath;


    @Value("${hdfs.savePath}")
    private String savePath;


    @Override
    public PagedResult<DataFile> selectPaged(DataFile dataFile, Page page) {
        return PageUtil.queryPaged(page,dataFile,dataFileMapper);
    }

    /**
     * 创建DataFile
     *
     * @param dataFile
     * @return
     */
    @Override
    public Integer insert(DataFile dataFile) {
        if (dataFile == null) {
            return 0;
        }
        return dataFileMapper.insert(dataFile);
    }


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        if (id == null) {
            return 0;
        }
        Integer result = dataFileMapper.deleteById(id);
        return result;

    }

    /**
     * 修改DataFile
     *
     * @param dataFile
     * @return
     */
    @Override
    public Integer updateByPrimaryKey(DataFile dataFile) {
        if (dataFile == null) {
            return 0;
        }
        return dataFileMapper.updateById(dataFile);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @Override
    public DataFile selectByPrimaryKey(Long id) {
        if (id == null) {
            return null;
        }
        DataFile dataFile = dataFileMapper.selectById(id);
        if (dataFile == null) {
            return null;
        }
        return dataFile;
    }


    /**
     * 批量插入
     *
     * @param list List<DataFile
     * @return Integer
     */
    @Override
    public Integer batchInsert(List<DataFile> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return dataFileMapper.batchInsert(list);
        }
    }

    /**
     * 批量更新
     *
     * @param list List<DataFile>
     * @return Integer
     */
    @Override
    public Integer batchUpdate(List<DataFile> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return dataFileMapper.batchInsert(list);
        }
    }

    /**
     * 批量删除
     *
     * @param list List<Long >
     * @return Integer
     */
    @Override
    public Integer deleteBatchIds(List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        } else {
            return dataFileMapper.deleteBatchIds(list);
        }
    }

    /**
     * 存在即更新
     *
     * @param dataFile DataFile
     * @return Integer
     */
    @Override
    public Integer upsert(DataFile dataFile) {

        if (dataFile == null) {
            return 0;
        } else {
            return dataFileMapper.upsert(dataFile);
        }

    }

    /**
     * 存在即更新，可选择具体属性
     *
     * @param dataFile DataFile
     * @return Integer
     */
    @Override
    public Integer upsertSelective(DataFile dataFile) {
        if (dataFile == null) {
            return 0;
        } else {
            return dataFileMapper.upsert(dataFile);
        }
    }

    /**
     * 条件查询
     *
     * @param dataFile DataFile
     * @return List<DataFile>
     */
    @Override
    public List<DataFile> query(DataFile dataFile) {
        if (dataFile == null) {
            return null;
        } else {
            return dataFileMapper.query(dataFile);
        }
    }

    /**
     * 查询总数
     *
     * @return Integer
     */
    @Override
    public Long queryTotalCount() {
        return dataFileMapper.queryTotalCount();
    }


    @Override
    public RespVO selectBybatchNums(List<String> batchNums, String tags) {
        QueryWrapper<DataFile> wrapper = Wrappers.query();
        if (tags != null) {
            String[] tag = tags.split(",");
            if (tag.length > 0) {
                wrapper.in("tags", tag);
            }
        }
        if (CollectionUtils.isEmpty(batchNums)) {
            return RespVOBuilder.failure("批次号为空");
        }
        wrapper.in("batch_num", batchNums);
        List<DataFile> dataFiles = dataFileMapper.selectList(wrapper);
        return RespVOBuilder.success(dataFiles);
    }


    @Override
    public RespVO<RespDataVO<DataFile>> upLoadFile(MultipartFile[] files, String remark, String tags) {
        String batchNumber = UuidUtils.generateShortUuid();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件不能为空");
        }
        //返回文件名为键值 文件url为key的map
     /*   Map<String, String> uploadsInfo = uploadToHdfs(storePath, savePath, files);
        if (uploadsInfo.isEmpty()) {
            ExceptionBuilder.operateFailException("上传文件失败");
        }*/
        List<DataFile> dataFileList = new ArrayList<>();
        Arrays.stream(files).forEach(f -> {
            //文件名
            String originalFilename = f.getOriginalFilename();
            //文件url
            //  String fileUrl = uploadsInfo.get(originalFilename);
            //文件后缀
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            DataFile dataFile = new DataFile();
            // dataFile.setFileUrl(fileUrl);
            dataFile.setFileType(ext);
            dataFile.setName(originalFilename);
            dataFile.setBatchNum(batchNumber);
            dataFile.setRemark(remark);
            dataFile.setTags(tags);
            dataFileList.add(dataFile);
        });

        Integer num = dataFileMapper.batchInsert(dataFileList);
        if (num == dataFileList.size()) {
            return RespVOBuilder.success(dataFileList);
        } else {
            return RespVOBuilder.failure("上传失败");
        }
    }


    @Override
    public Map<String, String> uploadToHdfs(String storePath, String savePath, MultipartFile[] files) {
        Map<String, String> fileNamemap = new HashMap<>();
        if (files == null || files.length == 0) {
            ExceptionBuilder.operateFailException("上传文件files不能为空");
        }
        Arrays.stream(files).forEach(f -> {
            String name = f.getOriginalFilename();
            String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            File file = new File(storePath);
            if (!file.exists()) {//目录不存在就创建
                file.mkdirs();
            }
            try {
                f.transferTo(new File(storePath + "/" + fileName + "." + subffix));//保存文件
                iHdfsService.uploadFileToHdfs(storePath + "/" + fileName + "." + subffix, savePath);
                fileNamemap.put(name, storePath + "/" + fileName + "." + subffix);
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionBuilder.operateFailException("上传HDFS文件失败");
            }
        });
        return fileNamemap;
    }


}
