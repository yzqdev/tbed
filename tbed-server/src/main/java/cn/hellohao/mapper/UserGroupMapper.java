package cn.hellohao.mapper;

import cn.hellohao.entity.UserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/20 13:45
 */
@Mapper
public interface UserGroupMapper extends BaseMapper<UserGroup> {
    UserGroup useridgetusergroup(@Param("userid") Integer userid);
    UserGroup idgetusergroup(@Param("id") Integer id);
    Integer addusergroup(UserGroup userGroup);
    Integer updateusergroup(UserGroup userGroup);
    Integer updateusergroupdefault(@Param("groupid") Integer groupid);
    Integer deleusergroup(@Param("userid") Integer userid);



}
