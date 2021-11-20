package cn.hellohao.dao;

import cn.hellohao.entity.Code;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeMapper extends BaseMapper<Code> {
    //查询扩容码
    Page<Code> selectCode(@Param("code") String code);
    Code selectCodekey(@Param("code") String code);
    //Integer selectCodekey(@Param("value") String value);
    //添加
    Integer addCode(Code code);
    //删除扩容码
    Integer deleteCode(@Param("code") String code);

}
