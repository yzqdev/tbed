package cn.hellohao.service.impl;

import cn.hellohao.dao.GroupMapper;
import cn.hellohao.dao.UserMapper;
import cn.hellohao.entity.SiteGroup;
import cn.hellohao.entity.SysUser;
import cn.hellohao.exception.CodeException;
import cn.hellohao.entity.Msg;
import cn.hellohao.service.GroupService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/19 16:30
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, SiteGroup> implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<SiteGroup> grouplist(Page<SiteGroup> page,Integer usertype) {
        return groupMapper.grouplist(page,usertype);
    }

    @Override
    public SiteGroup idgrouplist(String id) {
        return groupMapper.idgrouplist(id);
    }

    @Override
    public Msg addgroup(SiteGroup siteGroup) {
        final Msg msg = new Msg();
        if(siteGroup.getUserType()!=0){
            Integer count = groupMapper.GetCountFroUserType(siteGroup.getUserType());
            if(count==0){
                groupMapper.addgroup(siteGroup);
                msg.setInfo("添加成功");
            }else{
                msg.setCode("110401");
                msg.setInfo("分配的该用户组已存在。请勿重复分配。");
            }
        }else{
            groupMapper.addgroup(siteGroup);
            msg.setInfo("添加成功");
        }
        return msg;
    }

    @Override
    public Integer GetCountFroUserType(Integer usertype) {
        return groupMapper.GetCountFroUserType(usertype);
    }

    @Override
    @Transactional//默认遇到throw new RuntimeException(“…”);会回滚
    public Msg delegroup(String id) {
        Msg msg = new Msg();
        Integer ret = 0;
        ret = groupMapper.delegroup(id);
        if(ret>0){
            List<SysUser> sysUserList = userMapper.getUserListFromGroupId(id);
            for (SysUser sysUser : sysUserList) {
                SysUser u = new SysUser();
                u.setGroupId("1");
                u.setUid(sysUser.getUid());
                userMapper.change(u);
            }
            msg.setInfo("删除成功");
        }else{
            msg.setCode("500");
            msg.setInfo("删除成功");
            throw new CodeException("用户之没有设置成功。");
        }
        return msg;
    }

    @Override
    public Msg setgroup(SiteGroup siteGroup) {
        Msg msg = new Msg();
        if(siteGroup.getUserType()!=0){
            SiteGroup siteGroupFroUserType = groupMapper.getGroupFroUserType(siteGroup.getUserType());
            if(siteGroupFroUserType !=null){
                if(siteGroupFroUserType.getUserType().equals(siteGroup.getUserType())){
                    if(siteGroupFroUserType.getId().equals(siteGroup.getId())){
                        groupMapper.updateById(siteGroup);
                        msg.setInfo("修改成功");
                    }else{
                        msg.setCode("110401");
                        msg.setInfo("分配的该用户组已存在。请勿重复分配。");
                    }
                }else{
                    if(groupMapper.GetCountFroUserType(siteGroup.getUserType())>0){
                        msg.setCode("110401");
                        msg.setInfo("分配的该用户组已存在。请勿重复分配。");
                    }else{
                        groupMapper.updateById(siteGroup);
                        msg.setInfo("修改成功");
                    }
                }
            }else{
                groupMapper.updateById(siteGroup);
                msg.setInfo("修改成功");
            }
        }else{
            groupMapper.updateById(siteGroup);
            msg.setInfo("修改成功");
        }
        return msg;
    }

    @Override
    public SiteGroup getGroupFroUserType(Integer usertype) {
        return groupMapper.getGroupFroUserType(usertype);
    }

}
