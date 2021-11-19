package cn.hellohao.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.hellohao.entity.Keys;

import java.util.List;

@Mapper
public interface KeysMapper extends BaseMapper<Keys> {
    //查询密钥
    Keys selectKeys(@Param("id") Integer id);
    List<Keys> getStorageName();
    List<Keys> getStorage();
    //修改key
    Integer updateKey(Keys key);
    List<Keys> getKeys();

}
