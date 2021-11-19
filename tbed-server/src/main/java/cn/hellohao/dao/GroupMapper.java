package cn.hellohao.dao;

import cn.hellohao.entity.SiteGroup;
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
public interface GroupMapper extends BaseMapper<SiteGroup> {
    List<SiteGroup> grouplist(Integer usertype);
    SiteGroup idgrouplist(@Param("id") Integer id);
    Integer addgroup(SiteGroup siteGroup);
    Integer GetCountFroUserType(@Param("usertype") Integer usertype);
    Integer delegroup(@Param("id") Integer id);
    SiteGroup getGroupFroUserType(@Param("usertype") Integer usertype);
}
