package cn.hellohao.dao;

import cn.hellohao.entity.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/19 16:11
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {
    List<Group> grouplist(Integer usertype);
    Group idgrouplist(@Param("id") Integer id);
    Integer addgroup(Group group);
    Integer GetCountFroUserType(@Param("usertype") Integer usertype);
    Integer delegroup(@Param("id") Integer id);
    Integer setgroup(Group group);
    Group getGroupFroUserType(@Param("usertype") Integer usertype);
}
