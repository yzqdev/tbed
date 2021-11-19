package cn.hellohao.dao;

import cn.hellohao.entity.UploadConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadConfigMapper extends BaseMapper<UploadConfig> {
    UploadConfig getUpdateConfig();
    Integer setUpdateConfig(UploadConfig uploadConfig);
}
