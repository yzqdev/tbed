package cn.hellohao.controller;

import cn.hellohao.entity.*;
import cn.hellohao.entity.dto.ConfigDto;
import cn.hellohao.entity.dto.SysUserUpdateDto;
import cn.hellohao.entity.dto.UserSearchDto;
import cn.hellohao.service.*;
import cn.hellohao.service.impl.*;
import cn.hellohao.utils.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        Page<SysUser> page =new Page<>(pageNum,pageSize);
        Page<SysUser> users = userService.getuserlist(page,queryText);

        Map<String, Object> map = new HashMap<>(2);
        map.put("count", users.getTotal());
        map.put("users", users.getRecords());
        return map;

    }


    @PostMapping(value = "/updateUserInfo")//new
    @ResponseBody
    public Msg updateUserInfo(@RequestBody SysUserUpdateDto userUpdateDto) {
        final Msg msg = new Msg();
        try{
            Subject subject = SecurityUtils.getSubject();
            SysUser u = (SysUser) subject.getPrincipal();

           String id = userUpdateDto.getId();
            String email = userUpdateDto.getEmail();
            Long memory = userUpdateDto.getMemory();
            String groupid = userUpdateDto.getGroupId();
            Integer isok =userUpdateDto.getIsok();
            if(memory<0 || memory>1048576L){
                msg.setCode("500");
                msg.setInfo("容量不得超过1048576");
                return msg;
            }
            final SysUser sysUser = new SysUser();
            final SysUser sysUser2 = new SysUser();
            sysUser2.setId(id);
            SysUser sysUserInfo = userService.getUsers(sysUser2);
            sysUser.setId(id);
            sysUser.setEmail(email);
            sysUser.setMemory( memory*1024*1024);
            sysUser.setGroupId(groupid);
            if(sysUserInfo.getLevel()==1){
                sysUser.setIsok(isok==1?1:-1);
            }
            userService.changeUser(sysUser);
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
                SysUser u = new SysUser();
                u.setId(userIdList.getString(i));
                SysUser u2 = userService.getUsers(u);
                if(u2.getLevel()==1){
                    SysUser sysUser = new SysUser();
                    sysUser.setId(userIdList.getString(i));
                    sysUser.setIsok(-1);
                    userService.changeUser(sysUser);
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
    public Msg deleuser(@RequestBody String[] userIdList) {
        Msg msg = new Msg();
        try {

            boolean b = false;
            for (int i = 0; i < userIdList.length; i++) {
                SysUser u = new SysUser();
                u.setId(userIdList[i]);
                SysUser sysUser = userService.getUsers(u);
                if(sysUser.getLevel()==1){
                    userService.deleuser(userIdList[i]);
                }else{
                    b = true;
                }
            }
            if(b && userIdList.length==1){
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

    @PostMapping("/LoadInfo/{keyId}")//new
    @ResponseBody
    public Msg LoadInfo(@PathVariable("keyId") String keyId) {
        Msg msg = new Msg();
        try {

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
        String id = jsonObj.getString("id");
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

    @PostMapping("/getStorageById/{id}")//new
    @ResponseBody
    public Msg getselectkey(@PathVariable("id") String id) {
        Msg msg = new Msg();

        StorageKey storageKey = keysService.selectKeys(id);
        msg.setData(storageKey);
        return msg;
    }


    @PostMapping("/getSettingConfig") //new
    @ResponseBody
    public Msg getSettingConfig(@RequestParam(value = "data", defaultValue = "") String data) {
        final Msg msg = new Msg();
        final JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        SysUser u = (SysUser) subject.getPrincipal();
        try {
            UploadConfig uploadConfig = uploadConfigService.getUpdateConfig();
            Config config = configService.getSourceype();
            SysConfig sysConfig = sysConfigService.getstate();
            uploadConfig.setUserStorage(Long.valueOf(uploadConfig.getUserStorage())/1024/1024);
            uploadConfig.setVisitorStorage(Long.toString(Long.valueOf(uploadConfig.getVisitorStorage())/1024/1024));
            uploadConfig.setFileSizeTourists(Long.toString(Long.valueOf(uploadConfig.getFileSizeTourists())/1024/1024));
            uploadConfig.setFileSizeUser(Long.toString(Long.valueOf(uploadConfig.getFileSizeUser())/1024/1024));
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
            if((Long.valueOf(vm)<-1) || Long.valueOf(vm) > 104857600 || Long.valueOf(uploadConfig.getFileSizeTourists())<0 || Long.valueOf(uploadConfig.getFileSizeTourists()) > 5120
                    || Long.valueOf(uploadConfig.getUserStorage())<0 || Long.valueOf(uploadConfig.getUserStorage())>1048576
                    || Long.valueOf(uploadConfig.getFileSizeUser())<0 || Long.valueOf(uploadConfig.getFileSizeUser())>5120 ){
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
            uploadConfig.setFileSizeTourists(Long.toString(Long.valueOf(uploadConfig.getFileSizeTourists())*1024*1024));
            uploadConfig.setUserStorage( Long.valueOf(uploadConfig.getUserStorage())*1024*1024);
            uploadConfig.setFileSizeUser(Long.toString(Long.valueOf(uploadConfig.getFileSizeUser())*1024*1024));
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


    /**
     * 邮件配置
     *
     * @return {@link Msg}
     */
    @PostMapping(value = "/getOrderConfig")//new
    @ResponseBody
    public Msg emailConfig() {
        final Msg msg = new Msg();
        EmailConfig emailConfig = null;
        Imgreview imgreview = null;
        try {
            final JSONObject jsonObject = new JSONObject();
            emailConfig = emailConfigService.getEmail();
            imgreview = imgreviewService.selectByPrimaryKey("1");
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

        EmailConfig emailConfig = emailConfigService.getEmail();
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
