package cn.hellohao.controller;

import cn.hellohao.entity.Group;
import cn.hellohao.entity.Msg;
import cn.hellohao.service.GroupService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
        List<Group> groupList = groupService.grouplist(0);
        msg.setData(groupList);
        return msg;
    }

    @PostMapping(value = "/getGroupList")//new
    @ResponseBody
    public Map<String, Object> getgrouplist(@RequestParam(value = "data", defaultValue = "") String data) {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObj = JSONObject.parseObject(data);
        Integer pageNum = jsonObj.getInteger("pageNum");
        Integer pageSize = jsonObj.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<Group> group = null;
        try {
            group = groupService.grouplist(null);
            PageInfo<Group> rolePageInfo = new PageInfo<>(group);
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
        Group group = new Group();
        group.setGroupName(jsonObject.getString("groupname"));
        group.setKeyId(jsonObject.getInteger("keyid"));
        group.setUsertype(jsonObject.getInteger("usertype"));
        group.setCompress(jsonObject.getBoolean("compress")?1:0);
        Msg msg = groupService.addgroup(group);
        return msg;
    }

    @PostMapping("/updateGroup")//new
    @ResponseBody
    public Msg updategroup(@RequestParam(value = "data", defaultValue = "") String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        Group group = new Group();
        group.setId(jsonObject.getInteger("id"));
        if(jsonObject.getInteger("id")==1){
            group.setGroupName("默认群组");
            group.setUsertype(0);
        }else{
            group.setGroupName(jsonObject.getString("groupname"));
            group.setUsertype(jsonObject.getInteger("usertype"));
        }
        group.setKeyId(jsonObject.getInteger("keyid"));
        group.setCompress(jsonObject.getBoolean("compress")?1:0);
        Msg msg = groupService.setgroup(group);
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
