package cn.hellohao.service;

import cn.hellohao.entity.Images;
import cn.hellohao.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    //注册
    Integer register(User user);

    //登录
    Integer login(String email, String password,String uid);

    //获取用户信息
    User getUsers(User user);

    //插入图片
    Integer insertimg(Images img);

    //修改资料
    Integer change(User user);

    Integer changeUser(User user);

    //检查用户名是否重复
    Integer checkUsername(String username);

    Integer getUserTotal();

    List<User> getuserlist(String username);

    Integer deleuser(Integer id);

    //查询用户名或者邮箱是否存在
    Integer countusername(String username);

    Integer countmail(String email);

    Integer uiduser(String uid);

    User getUsersMail(String uid);
    Integer setisok (User user);
    Integer setmemory(User user);
    User getUsersid(Integer id);
    List<User> getuserlistforgroupid(Integer groupid);
}
