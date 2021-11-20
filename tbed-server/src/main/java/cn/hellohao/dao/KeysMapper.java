package cn.hellohao.dao;

import cn.hellohao.entity.StorageKey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KeysMapper extends BaseMapper<StorageKey> {
    //查询密钥
    StorageKey selectKeys(@Param("id") Integer id);
    List<StorageKey> getStorageName();
    List<StorageKey> getStorage();
    //修改key
    List<StorageKey> getKeys();

}
