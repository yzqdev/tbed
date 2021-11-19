package cn.hellohao.utils;

import cn.hellohao.entity.SiteGroup;
import cn.hellohao.entity.User;
import cn.hellohao.service.impl.GroupServiceImpl;
import cn.hellohao.service.impl.UserGroupServiceImpl;
import cn.hellohao.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/20 14:10
 */
@Component
public class GetCurrentSource {
    @Autowired
    private GroupServiceImpl groupServiceImpl;
    @Autowired
    private UserGroupServiceImpl userGroupServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    private static GroupServiceImpl groupService;
    private static UserGroupServiceImpl userGroupService;
    private static UserServiceImpl userService;

    @PostConstruct
    public void init() {
        groupService =groupServiceImpl;
        userGroupService = userGroupServiceImpl;
        userService = userServiceImpl;
    }



    public static SiteGroup GetSource(Integer userid) {
        //UserType 0-未分配 1-游客 2-用户 3-管理员
        User user =null;
        if(userid!=null){
            User u = new User();
            u.setId(userid);
            user = userService.getUsers(u);
        }
        SiteGroup siteGroup =null;
        if(user==null){
            //游客
            Integer count = groupService.GetCountFroUserType(1);
            if(count>0){
                siteGroup = groupService.getGroupFroUserType(1);
            }else{
                siteGroup = groupService.idgrouplist(1);
            }
        }else{
            //用户
            if(user.getGroupId()!=1){
                //说明自定义过的优先
                siteGroup = groupService.idgrouplist(user.getGroupId());
            }else{
                //默认的，用的是group主键为1的  但是还需要看看用户组有没有设置，比如管理员 用户
                if(user.getLevel()>1){
                    //先查询管理员用户组有没有 如果有就用 没有就默认
                    Integer count = groupService.GetCountFroUserType(3);
                    if(count>0){
                        siteGroup = groupService.getGroupFroUserType(3);
                    }else{
                        siteGroup = groupService.idgrouplist(1);
                    }
                }else{
                    //先查询普通用户组有没有 如果有就用 没有就默认
                    Integer count = groupService.GetCountFroUserType(2);
                    if(count>0){
                        siteGroup = groupService.getGroupFroUserType(2);

                    }else{
                        siteGroup = groupService.idgrouplist(1);
                    }
                }
            }
        }
        return siteGroup;
    }


}
