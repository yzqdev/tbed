package cn.hellohao.service;

import cn.hellohao.entity.SiteGroup;
import cn.hellohao.entity.Msg;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/19 16:29
 */
@Service
public interface GroupService extends IService<SiteGroup> {
    Page<SiteGroup> grouplist(Page<SiteGroup> page,Integer usertype);
    SiteGroup idgrouplist(Integer id);
    Msg addgroup(SiteGroup siteGroup);
    Integer GetCountFroUserType(Integer usertype);
    Msg delegroup(Integer id);
    Msg setgroup(SiteGroup siteGroup);
    SiteGroup getGroupFroUserType(Integer usertype);
}
