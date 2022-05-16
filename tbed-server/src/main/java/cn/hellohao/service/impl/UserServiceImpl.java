package cn.hellohao.service.impl;

import cn.hellohao.dao.CodeMapper;
import cn.hellohao.dao.UserMapper;
import cn.hellohao.entity.SysUser;
import cn.hellohao.exception.CodeException;
import cn.hellohao.service.UserService;
import cn.hellohao.utils.Print;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CodeMapper codeMapper;
    @Override
    public Integer register(SysUser sysUser) {
        // TODO Auto-generated method stub
        return userMapper.register(sysUser);
    }

    @Override
    public Integer login(String email, String password,String uid) {
        // TODO Auto-generated method stub
        return userMapper.login(email, password,uid);
    }

    @Override
    public SysUser getUsers(SysUser sysUser) {
        // TODO Auto-generated method stub
        return userMapper.getUsers(sysUser);
    }


    @Override
    public Integer change(SysUser sysUser) {
        // TODO Auto-generated method stub
        return userMapper.change(sysUser);
    }


    @Override
    public Integer changeUser(SysUser sysUser) {
        return userMapper.changeUser(sysUser);
    }

    @Override
    public Integer checkUsername(String username) {
        // TODO Auto-generated method stub
        return userMapper.checkUsername(username);
    }

    @Override
    public Integer getUserTotal() {
        // TODO Auto-generated method stub
        return userMapper.getUserTotal();
    }

    @Override
    public Integer deleuser(String id) {
        return userMapper.deleuser(id);
    }

    @Override
    public Integer countusername(String username) {
        return userMapper.countusername(username);
    }

    @Override
    public Integer countmail(String email) {
        return userMapper.countmail(email);
    }

    @Override
    public Page<SysUser> getuserlist(Page<SysUser> page, String username) {
        return userMapper.getUserList(page,username);
    }

    @Override
    public Integer uiduser(String uid) {
        return userMapper.uiduser(uid);
    }

    @Override
    public SysUser getUsersMail(String uid) {
        return userMapper.getUsersMail(uid);
    }

    @Override
    public Integer setisok(SysUser sysUser) {
        return userMapper.setisok(sysUser);
    }

    @Override
    public Integer setmemory(SysUser sysUser) {
        return userMapper.setmemory(sysUser);
    }

    @Override
    public SysUser getUsersid(String id) {
        return userMapper.getUsersid(id);
    }

    @Override
    public List<SysUser> getuserlistforgroupid(String groupid) {
        return userMapper.getUserListFromGroupId(groupid);
    }



    /**
     * 默认遇到throw new RuntimeException(“…”);会回滚
     *
     * @param sysUser       用户
     * @param codestring codestring
     * @return {@link Integer}
     */
    @Transactional
    public Integer usersetmemory(SysUser sysUser, String codestring) {
        Integer ret = userMapper.changeUser(sysUser);
        if(ret<=0){
            Print.warning("用户空间没有设置成功。回滚");
            throw new CodeException("用户没有设置成功。");
        }else{
            ret = codeMapper.deleteCode(codestring);
        }
        return ret;
    }


}
