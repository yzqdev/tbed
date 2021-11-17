package cn.hellohao.dao;

import cn.hellohao.entity.Config;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigMapper {
    Config getSourceype();
    Integer setSourceype(Config config);
}
