package cn.hellohao.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.hellohao.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //注册
    Integer register(User user);

    //登录
    Integer login(@Param("email") String email, @Param("password") String password,@Param("uid") String uid);

    //获取用户信息
    User getUsers(User user);



    //修改资料
    Integer change(User user);

    Integer changeUser(User user);

    /**
     * 检查用户名是否重复
     *
     * @param username 用户名
     * @return {@link Integer}
     */
    Integer checkUsername(@Param("username") String username);

    Integer getUserTotal();

    Page<User> getUserList(@Param("page") Page<User> page, String username);


    /**
     * 刪除用戶
     *
     * @param id id
     * @return {@link Integer}
     */
    Integer deleuser(@Param("id") String id);


    /**
     * 查询用户名或者邮箱是否存在
     *
     * @param username 用户名
     * @return {@link Integer}
     */
    Integer countusername(@Param("username") String username);

    Integer countmail(@Param("email") String email);

    Integer uiduser(@Param("uid") String uid);

    User getUsersMail(@Param("uid") String uid);
    Integer setisok (User user);

    Integer setmemory(User user);
    User getUsersid(@Param("id") String id);

    List<User> getUserListFromGroupId(@Param("groupid") String groupid);

}
