package cn.hellohao.mapper;

import cn.hellohao.entity.SiteGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/19 16:11
 */
@Mapper
public interface GroupMapper extends BaseMapper<SiteGroup> {
    Page<SiteGroup> grouplist(@Param("page")Page<SiteGroup> page, Integer usertype);
    SiteGroup idgrouplist(@Param("id") String id);
    Integer addgroup(SiteGroup siteGroup);
    Integer GetCountFroUserType(@Param("usertype") Integer usertype);
    Integer delegroup(@Param("id") String id);
    SiteGroup getGroupFroUserType(@Param("usertype") Integer usertype);
}
