package cn.hellohao.controller;

import cn.hellohao.entity.SiteGroup;
import cn.hellohao.entity.Msg;
import cn.hellohao.entity.dto.PageDto;
import cn.hellohao.service.GroupService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/19 16:35
 */
@Controller
@RequestMapping("/admin/root")
public class GroupController {
    @Autowired
    private GroupService groupService;


    @PostMapping("/getGrouplistForUsers") //new
    @ResponseBody
    public Msg getGrouplistForUsers() {
        Msg msg = new Msg();
        List<SiteGroup> siteGroupList = groupService.grouplist(0);
        msg.setData(siteGroupList);
        return msg;
    }

    @PostMapping(value = "/getGroupList")//new
    @ResponseBody
    public Map<String, Object> getgrouplist(@RequestBody PageDto data ) {
        Map<String, Object> map = new HashMap<>();

        int pageNum = data.getPageNum();
        Integer pageSize = data.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SiteGroup> siteGroup = null;
        try {
            siteGroup = groupService.grouplist(null);
            PageInfo<SiteGroup> rolePageInfo = new PageInfo<>(siteGroup);
            map.put("code", 200);
            map.put("info", "");
            map.put("count", rolePageInfo.getTotal());
            map.put("data", rolePageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("info", "获取数据异常");
        }
        return map;
    }

    @PostMapping(value = "/addGroup")//new
    @ResponseBody
    public Msg addisgroup(@RequestParam(value = "data", defaultValue = "") String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        SiteGroup siteGroup = new SiteGroup();
        siteGroup.setGroupName(jsonObject.getString("groupname"));
        siteGroup.setKeyId(jsonObject.getInteger("keyid"));
        siteGroup.setUserType(jsonObject.getInteger("usertype"));
        siteGroup.setCompress(jsonObject.getBoolean("compress")?1:0);
        Msg msg = groupService.addgroup(siteGroup);
        return msg;
    }

    @PostMapping("/updateGroup")//new
    @ResponseBody
    public Msg updategroup(@RequestBody SiteGroup siteGroup) {



        if(siteGroup.getId()==1){
            siteGroup.setGroupName("默认群组");
            siteGroup.setUserType(0);
        }else{
            //siteGroup.setGroupName(jsonObject.getString("groupname"));
            //siteGroup.setUserType(jsonObject.getInteger("usertype"));
        }
        System.out.println("updateGroup");
        System.out.println(siteGroup.getCompress());
        if (siteGroup.getCompress().toString()=="true") {
            siteGroup.setCompress(1);
        }
        else {
            siteGroup.setCompress(0);
        }
        Msg msg = groupService.setgroup(siteGroup);
        return msg;
    }

    @PostMapping(value = "/deleGroup")//new
    @ResponseBody
    public Msg delegroup(@RequestParam(value = "data", defaultValue = "") String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer id = jsonObject.getInteger("id");
        Msg msg = null;
        if(id!=1){
            msg = groupService.delegroup(id);
            return msg;
        }else{
            final Msg msg2 = new Msg();
            msg2.setCode("500");
            msg2.setInfo("默认群组不可删除");
            return msg2;
        }
    }



}
