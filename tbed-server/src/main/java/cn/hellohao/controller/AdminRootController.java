package cn.hellohao.controller;

import cn.hellohao.entity.*;
import cn.hellohao.entity.dto.ConfigDto;
import cn.hellohao.entity.dto.UserSearchDto;
import cn.hellohao.entity.dto.UserUpdateDto;
import cn.hellohao.service.*;
import cn.hellohao.service.impl.*;
import cn.hellohao.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/root")
public class AdminRootController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private KeysService keysService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailConfigService emailConfigService;
    @Autowired
    private UploadConfigService uploadConfigService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private ImgreviewService imgreviewService;


    @PostMapping(value = "/getUserList")//new
    @ResponseBody
    public Map<String, Object> getUserList(@RequestBody UserSearchDto userSearchDto) {
        Integer pageNum =userSearchDto.getPageNum();
        Integer pageSize =userSearchDto.getPageSize();
        String queryText = userSearchDto.getQueryText();
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.getuserlist(queryText);
        PageInfo<User> rolePageInfo = new PageInfo<>(users);
        Map<String, Object> map = new HashMap<>();
        map.put("count", rolePageInfo.getTotal());
        map.put("users", rolePageInfo.getList());
        return map;

    }


    @PostMapping(value = "/updateUserInfo")//new
    @ResponseBody
    public Msg updateUserInfo(@RequestBody UserUpdateDto userUpdateDto) {
        final Msg msg = new Msg();
        try{
            Subject subject = SecurityUtils.getSubject();
            User u = (User) subject.getPrincipal();

            Integer id = userUpdateDto.getId();
            String email = userUpdateDto.getEmail();
            Long memory = userUpdateDto.getMemory();
            Integer groupid = userUpdateDto.getGroupId();
            Integer isok =userUpdateDto.getIsok();
            if(memory<0 || memory>1048576L){
                msg.setCode("500");
                msg.setInfo("容量不得超过1048576");
                return msg;
            }
            final User user = new User();
            final User user2 = new User();
            user2.setId(id);
            User userInfo = userService.getUsers(user2);
            user.setId(id);
            user.setEmail(email);
            user.setMemory( memory*1024*1024);
            user.setGroupId(groupid);
            if(userInfo.getLevel()==1){
                user.setIsok(isok==1?1:-1);
            }
            userService.changeUser(user);
            msg.setInfo("修改成功");
        }catch (Exception e){
            msg.setCode("500");
            msg.setInfo("修改失败");
            e.printStackTrace();
        }
        return msg;
    }


    @PostMapping("/disableUser")//new
    @ResponseBody
    public Msg disableUser(@RequestParam(value = "data", defaultValue = "") String data) {
        Msg msg = new Msg();
        try {
            JSONObject jsonObj = JSONObject.parseObject(data);
            JSONArray userIdList = jsonObj.getJSONArray("arr");
            for (int i = 0; i < userIdList.size(); i++) {
                User u = new User();
                u.setId(userIdList.getInteger(i));
                User u2 = userService.getUsers(u);
                if(u2.getLevel()==1){
                    User user = new User();
                    user.setId(userIdList.getInteger(i));
                    user.setIsok(-1);
                    userService.changeUser(user);
                }

            }
            msg.setInfo("所选用户已被禁用");
        }catch (Exception e){
            e.printStackTrace();
            msg.setInfo("系统错误");
            msg.setCode("500");
        }
        return msg;
    }

    @PostMapping("/deleUser")//new
    @ResponseBody
    public Msg deleuser(@RequestParam(value = "data", defaultValue = "") String data) {
        Msg msg = new Msg();
        try {
            JSONObject jsonObj = JSONObject.parseObject(data);
            JSONArray userIdList = jsonObj.getJSONArray("arr");
            boolean b = false;
            for (int i = 0; i < userIdList.size(); i++) {
                User u = new User();
                u.setId(userIdList.getInteger(i));
                User user = userService.getUsers(u);
                if(user.getLevel()==1){
                    userService.deleuser(userIdList.getInteger(i));
                }else{
                    b = true;
                }
            }
            if(b && userIdList.size()==1){
                msg.setInfo("管理员账户不可删除");
            }else {
                msg.setInfo("用户已删除成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setInfo("系统错误");
            msg.setCode("500");
        }
        return msg;
    }

    @PostMapping("/getKeysList") //new
    @ResponseBody
    public Msg getKeysList() {
        Msg msg = new Msg();
        List<StorageKey> list = keysService.getKeys();
        msg.setData(list);
        return msg;
    }

    @PostMapping("/LoadInfo")//new
    @ResponseBody
    public Msg LoadInfo(@RequestParam(value = "data", defaultValue = "") String data) {
        Msg msg = new Msg();
        try {
            JSONObject jsonData = JSONObject.parseObject(data);
            Integer keyId = jsonData.getInteger("keyId");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",keyId);
            StorageKey key = keysService.selectKeys(keyId);
            Integer ret = 0;
            if(key.getStorageType()==1){
                ret = NOSImageupload.Initialize(key);
            }else if (key.getStorageType()==2){
                ret = OSSImageupload.Initialize(key);
            }else if(key.getStorageType()==3){
                ret = USSImageupload.Initialize(key);
            }else if(key.getStorageType()==4){
                ret = KODOImageupload.Initialize(key);
            }else if(key.getStorageType()==6){
                ret = COSImageupload.Initialize(key);
            }else if(key.getStorageType()==7){
                ret = FTPImageupload.Initialize(key);
            }else if(key.getStorageType()==8){
                ret = UFileImageupload.Initialize(key);
            }
            Long l = imgService.getsourcememory(keyId);
            jsonObject.put("isok",ret);
            jsonObject.put("storagetype",key.getStorageType());
            if(l==null){
                jsonObject.put("usedCapacity",0);
            }else{
                jsonObject.put("usedCapacity", SetFiles.readableFileSize(l));
            }
            msg.setData(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode("500");
        }
        return msg;
    }


    @PostMapping("/updateStorage") //new
    @ResponseBody
    public Msg updateStorage(@RequestParam(value = "data", defaultValue = "") String data) {
        JSONObject jsonObj = JSONObject.parseObject(data);
        Integer id = jsonObj.getInteger("id");
        String AccessKey = jsonObj.getString("AccessKey");
        String AccessSecret = jsonObj.getString("AccessSecret");
        String Endpoint = jsonObj.getString("Endpoint");
        String Bucketname = jsonObj.getString("Bucketname");
        String RequestAddress = jsonObj.getString("RequestAddress");
        Integer storageType = jsonObj.getInteger("storageType");
        String keyname = jsonObj.getString("keyname");
        StorageKey storageKey = new StorageKey();
        storageKey.setId(id);
        storageKey.setAccessKey(AccessKey);
        storageKey.setAccessSecret(AccessSecret);
        storageKey.setEndpoint(Endpoint);
        storageKey.setBucketName(Bucketname);
        storageKey.setRequestAddress(RequestAddress);
        storageKey.setStorageType(storageType);
        storageKey.setKeyName(keyname);
        Msg msg = keysService.updateKey(storageKey);
        return msg;
    }

    @PostMapping("/getStorageById")//new
    @ResponseBody
    public Msg getselectkey(@RequestParam(value = "data", defaultValue = "") String data) {
        Msg msg = new Msg();
        JSONObject jsonObj = JSONObject.parseObject(data);
        Integer keyid = jsonObj.getInteger("id");
        StorageKey storageKey = keysService.selectKeys(keyid);
        msg.setData(storageKey);
        return msg;
    }


    @PostMapping("/getSettingConfig") //new
    @ResponseBody
    public Msg getSettingConfig(@RequestParam(value = "data", defaultValue = "") String data) {
        final Msg msg = new Msg();
        final JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        User u = (User) subject.getPrincipal();
        try {
            UploadConfig uploadConfig = uploadConfigService.getUpdateConfig();
            Config config = configService.getSourceype();
            SysConfig sysConfig = sysConfigService.getstate();
            uploadConfig.setUserStorage(Long.valueOf(uploadConfig.getUserStorage())/1024/1024);
            uploadConfig.setVisitorStorage(Long.toString(Long.valueOf(uploadConfig.getVisitorStorage())/1024/1024));
            uploadConfig.setFilesizetourists(Long.toString(Long.valueOf(uploadConfig.getFilesizetourists())/1024/1024));
            uploadConfig.setFilesizeuser(Long.toString(Long.valueOf(uploadConfig.getFilesizeuser())/1024/1024));
            jsonObject.put("uploadConfig",uploadConfig);
            jsonObject.put("config",config);
            jsonObject.put("sysConfig",sysConfig);
            msg.setData(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            msg.setCode("110500");
            msg.setInfo("操作失败");
        }
        return msg;
    }


    @PostMapping("/updateConfig")//new
    @ResponseBody
    public Msg updateConfig(@RequestBody ConfigDto configDto) {
        Msg msg = new Msg();
        try {

            UploadConfig uploadConfig = configDto.getUploadConfig();
            String vm =  uploadConfig.getVisitorStorage();
            if((Long.valueOf(vm)<-1) || Long.valueOf(vm) > 104857600 || Long.valueOf(uploadConfig.getFilesizetourists())<0 || Long.valueOf(uploadConfig.getFilesizetourists()) > 5120
                    || Long.valueOf(uploadConfig.getUserStorage())<0 || Long.valueOf(uploadConfig.getUserStorage())>1048576
                    || Long.valueOf(uploadConfig.getFilesizeuser())<0 || Long.valueOf(uploadConfig.getFilesizeuser())>5120 ){
                msg.setInfo("你输入的值不正确");
                msg.setCode("500");
                return  msg;
            }
            Config config =configDto.getConfig();
            SysConfig sysConfig = configDto.getSysConfig();
            if(Integer.valueOf(vm)==-1){
                uploadConfig.setVisitorStorage("-1");
            }else{
                uploadConfig.setVisitorStorage(Long.toString(Long.valueOf(uploadConfig.getVisitorStorage())*1024*1024));
            }
            uploadConfig.setFilesizetourists(Long.toString(Long.valueOf(uploadConfig.getFilesizetourists())*1024*1024));
            uploadConfig.setUserStorage( Long.valueOf(uploadConfig.getUserStorage())*1024*1024);
            uploadConfig.setFilesizeuser(Long.toString(Long.valueOf(uploadConfig.getFilesizeuser())*1024*1024));
            uploadConfigService.setUpdateConfig(uploadConfig);
            configService.setSourceype(config);
            sysConfigService.setstate(sysConfig);
            msg.setInfo("配置保存成功");
        }catch (Exception e){
            e.printStackTrace();
            msg.setInfo("操作出现异常");
            msg.setCode("500");
        }
        return msg;
    }


    @PostMapping(value = "/getOrderConfig")//new
    @ResponseBody
    public Msg emailconfig() {
        final Msg msg = new Msg();
        EmailConfig emailConfig = null;
        Imgreview imgreview = null;
        try {
            final JSONObject jsonObject = new JSONObject();
            emailConfig = emailConfigService.getemail();
            imgreview = imgreviewService.selectByPrimaryKey(1);
            jsonObject.put("emailConfig",emailConfig);
            jsonObject.put("imgreview",imgreview);
            msg.setData(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            msg.setCode("110500");
            msg.setInfo("获取相关配置信息失败");
        }
        return msg;
    }


    @PostMapping("/updateEmailConfig") //new
    @ResponseBody
    public Msg updateemail(@RequestBody EmailConfig emailConfig ) {
        final Msg msg = new Msg();
        try {

            if(null==emailConfig.getId() || null==emailConfig.getEmailName()  || null==emailConfig.getEmailUrl() || null==emailConfig.getEmails()
                    || null==emailConfig.getEmailKey()  || null==emailConfig.getPort() || null==emailConfig.getUsing()
                    || emailConfig.getEmailName().equals("")  || emailConfig.getEmailUrl().equals("")  || emailConfig.getEmails().equals("")
                    || emailConfig.getEmailKey().equals("")   || emailConfig.getPort().equals("")){
                msg.setCode("110400");
                msg.setInfo("各参数不能为空");
                return msg;
            }
            emailConfigService.updateemail(emailConfig);
            msg.setInfo("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            msg.setCode("110500");
            msg.setInfo("保存过程出现错误");
        }
        return msg;
    }

    @PostMapping("/mailTest/{toMail}") //new
    @ResponseBody
    public Msg mailTest(@PathVariable("toMail") String toMail ) {
        Msg msg = new Msg();

        EmailConfig emailConfig = emailConfigService.getemail();
        if(null==emailConfig.getEmails() || null==emailConfig.getEmailKey() || null==emailConfig.getEmailUrl()
                || null==emailConfig.getPort()  || null==emailConfig.getEmailName() || null==toMail){
//        if(jsonObj.size()==0){
            msg.setCode("110400");
            msg.setInfo("邮箱配置参数不能为空");
        }else{
            msg = NewSendEmail.sendTestEmail(emailConfig, toMail);
        }
        return msg;
    }


}
