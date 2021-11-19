package cn.hellohao.dao;

import cn.hellohao.entity.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/15 13:33
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    SysConfig getstate();
    Integer setstate(SysConfig sysConfig);
}
