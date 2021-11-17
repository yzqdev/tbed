package cn.hellohao.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.hellohao.auth.filter.SubjectFilter;
import cn.hellohao.auth.token.JWTUtil;
import cn.hellohao.config.SysName;
import cn.hellohao.entity.*;
import cn.hellohao.service.*;
import cn.hellohao.utils.*;
import cn.hutool.core.util.HexUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailConfigService emailConfigService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private UploadConfigService uploadConfigService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    IRedisService iRedisService;

    @PostMapping("/register")
    @ResponseBody
    public Msg Register(HttpServletRequest request, @RequestParam(value = "data", defaultValue = "") String data) {//Validated
        Msg msg = new Msg();
        JSONObject jsonObj = JSONObject.parseObject(data);
        String username = jsonObj.getString("username");
        String email = jsonObj.getString("email");
        String password = Base64Encryption.encryptBASE64(jsonObj.getString("password").getBytes());
        String userIP = GetIPS.getIpAddr(request);
        String verifyCodeForRegister = jsonObj.getString("verifyCode");
        Object redis_verifyCodeForRegister = iRedisService.getValue(userIP+"_hellohao_verifyCodeForRegister");
        if(!SetText.checkEmail(email)){
            msg.setCode("110403");
            msg.setInfo("邮箱格式不正确");
            return msg;
        }
        String regex = "^\\w+$";
        if(username.length()>20 || !username.matches (regex)){
            msg.setCode("110403");
            msg.setInfo("用户名不得超过20位字符");
            return msg;
        }
        if(null==redis_verifyCodeForRegister){
            msg.setCode("4035");
            msg.setInfo("验证码已失效，请重新弄获取。");
            return msg;
        }else if(null==verifyCodeForRegister){
            msg.setCode("4036");
            msg.setInfo("验证码不能为空。");
            return msg;
        }
        if((redis_verifyCodeForRegister.toString().toLowerCase()).compareTo((verifyCodeForRegister.toLowerCase()))==0){
            User user = new User();
            UploadConfig updateConfig = uploadConfigService.getUpdateConfig();
            EmailConfig emailConfig = emailConfigService.getemail();
            Integer countusername = userService.countusername(username);
            Integer countmail = userService.countmail(email);
            SysConfig sysConfig = sysConfigService.getstate();
            if(sysConfig.getRegister()!=1){
                msg.setCode("110403");
                msg.setInfo("本站已暂时关闭用户注册功能");
                return msg;
            }
            if(countusername == 1 || !SysName.CheckSysName(username)){
                msg.setCode("110406");
                msg.setInfo("此用户名已存在");
                return msg;
            }
            if(countmail == 1){
                msg.setCode("110407");
                msg.setInfo("此邮箱已被注册");
                return msg;
            }
            String uid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String birthder = df.format(new Date());// new Date()为获取当前系统时间
            user.setLevel(1);
            user.setUid(uid);
            user.setBirthday(birthder);
            user.setMemory(updateConfig.getUserStorage());
            user.setGroupId(1);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            Config config = configService.getSourceype();
            Integer type = 0;
            if(emailConfig.getUsing()==1){
                user.setIsok(0);
                Thread thread = new Thread() {
                    public void run() {
                        Integer a = NewSendEmail.sendEmail(emailConfig,user.getUsername(), uid, user.getEmail(),config);
                    }
                };
                thread.start();
                msg.setInfo("注册成功,请注意查收邮箱尽快激活账户");
            }else{
                user.setIsok(1);
                msg.setInfo("注册成功,快去登陆吧");
            }
            userService.register(user);
        }else{
            msg.setCode("110408");
            msg.setInfo("验证码不正确");//失效也要处理。
        }
        return msg;
    }

    @PostMapping("/login")//new
    @ResponseBody
    public Msg login(HttpServletRequest request,@RequestParam(value = "data", defaultValue = "") String data) {
        Msg msg = new Msg();
        JSONObject jsonObj = JSONObject.parseObject(data);
        String email = jsonObj.getString("email");
        String password = Base64Encryption.encryptBASE64(jsonObj.getString("password").getBytes());
        String verifyCode = jsonObj.getString("verifyCode");
        String userIP = GetIPS.getIpAddr(request);
        Object redis_VerifyCode = iRedisService.getValue(userIP+"_hellohao_verifyCode");
        if(null==redis_VerifyCode){
            msg.setCode("4035");
            msg.setInfo("验证码已失效，请重新弄获取。");
            return msg;
        }else if(null==verifyCode){
            msg.setCode("4036");
            msg.setInfo("验证码不能为空。");
            return msg;
        }
        if((redis_VerifyCode.toString().toLowerCase()).compareTo((verifyCode.toLowerCase()))==0){
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken tokenOBJ = new UsernamePasswordToken(email,password);
            tokenOBJ.setRememberMe(true);
            try {
                subject.login(tokenOBJ);
                SecurityUtils.getSubject().getSession().setTimeout(3600000);//一小时
                JSONObject jsonObject = new JSONObject();
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                if(user.getIsok()==0){
                    msg.setInfo("你的账号暂未激活");
                    msg.setCode("110403");
                    return msg;
                }
                if(user.getIsok()<0){
                    msg.setInfo("你的账户已被冻结");
                    msg.setCode("110403");
                    return msg;
                }
                String token = JWTUtil.createToken(user);
                Subject su = SecurityUtils.getSubject();
                System.out.println("当前用户角色：admin:"+su.hasRole("admin"));
                msg.setInfo("登录成功");
                jsonObject.put("token",token);
                jsonObject.put("RoleLevel",user.getLevel()==2?"admin":"user");
                jsonObject.put("userName",user.getUsername());
                msg.setData(jsonObject);
                return msg;
            } catch (UnknownAccountException e) {
                //此异常说明用户名不存在
                msg.setCode("4000");
                msg.setInfo("登录邮箱不存在");
                System.err.println("邮箱不存在");
                e.printStackTrace();
                return msg;
            }catch (IncorrectCredentialsException e) {
                msg.setCode("4000");
                msg.setInfo("登录密码错误");
                System.err.println("密码不存在");
                e.printStackTrace();
                return msg;
            }catch (Exception e) {
                msg.setCode("5000");
                msg.setInfo("登录失败");
                System.err.println("登录失败");
                e.printStackTrace();
                return msg;
            }
        }else{
            msg.setCode("40034");
            msg.setInfo("验证码不正确");//失效也要处理。
        }
        return msg;
    }


    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public String activation(Model model, HttpServletRequest request, HttpSession session, String activation, String username) {
        Config config = configService.getSourceype();
        Integer ret = 0;
        User u2 = new User();
        u2.setUid(activation);
        User user = userService.getUsers(u2);
        model.addAttribute("webhost",SubjectFilter.WEBHOST);
        if (user != null && user.getIsok() == 0) {
            userService.uiduser(activation);
            model.addAttribute("title","激活成功");
            model.addAttribute("name","Hi~"+username);
            model.addAttribute("note","您的账号已成功激活看");
            return "msg";
        } else {
            model.addAttribute("title","操作无效");
            model.addAttribute("name","该页面为无效页面");
            model.addAttribute("note","请返回首页");
            return "msg";
        }
    }

    @PostMapping(value = "/logout")//new
    @ResponseBody
    public Msg exit(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Msg msg = new Msg();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        msg.setInfo("退出成功");
        Print.Normal("用户账号退出成功");
        return msg;
    }

    @PostMapping("/retrievePass") //new
    @ResponseBody
    public Msg retrievePass(HttpServletRequest request, @RequestParam(value = "data", defaultValue = "") String data) {
        Msg msg = new Msg();
        try {
            JSONObject jsonObj = JSONObject.parseObject(data);
            String email = jsonObj.getString("email");
            String retrieveCode = jsonObj.getString("retrieveCode");
            String userIP = GetIPS.getIpAddr(request);
            Object redis_verifyCodeForEmailRetrieve = iRedisService.getValue(userIP+"_hellohao_verifyCodeForEmailRetrieve");

            EmailConfig emailConfig = emailConfigService.getemail();
            if(null==redis_verifyCodeForEmailRetrieve){
                msg.setCode("4035");
                msg.setInfo("验证码已失效，请重新弄获取。");
                return msg;
            }else if(null==retrieveCode){
                msg.setCode("4036");
                msg.setInfo("验证码不能为空。");
                return msg;
            }
            if((redis_verifyCodeForEmailRetrieve.toString().toLowerCase()).compareTo((retrieveCode.toLowerCase()))!=0){
                msg.setCode("40034");
                msg.setInfo("验证码不正确");
                return msg;
            }

            Integer ret = userService.countmail(email);
            if(ret>0){
                if(emailConfig.getUsing()==1){
                    User u2 = new User();
                    u2.setEmail(email);
                    User user = userService.getUsers(u2);
                    if(user.getIsok()==-1){
                        msg.setCode("110110");
                        msg.setInfo("当前用户已被冻结，禁止操作");
                        return msg;
                    }
                    Config config = configService.getSourceype();
                    Thread thread = new Thread() {
                        public void run() {
                            Integer a = NewSendEmail.sendEmailFindPass(emailConfig,user.getUsername(), user.getUid(), user.getEmail(),config);//SendEmail.sendEmailT(message, user.getUsername(), user.getUid(), user.getEmail(),emailConfig,config);
                        }
                    };
                    thread.start();
                    msg.setInfo("重置密码的验证链接已发送至该邮箱，请前往邮箱验证并重置密码。【若长时间未收到邮件，请检查垃圾箱】");
                }else{
                    msg.setCode("400");
                    msg.setInfo("本站暂未开启邮箱服务，请联系管理员");
                }
            }else{
                msg.setCode("110404");
                msg.setInfo("未找到邮箱所在的用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode("110500");
            msg.setInfo("系统发生错误");
        }
        return msg;
    }

    @RequestMapping(value = "/retrieve", method = RequestMethod.GET) //new
    public String retrieve(Model model, String activation,String cip) {
        Integer ret = 0;
        try {
            User u2 = new User();
            u2.setUid(activation);
            User user = userService.getUsers(u2);
            user.setIsok(1);
            String new_pass = HexUtil.decodeHexStr(cip);//解密密码
            user.setPassword(Base64Encryption.encryptBASE64(new_pass.getBytes()));
            String uid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            user.setUid(uid);
            if (user != null) {
                Integer r = userService.changeUser(user);
                model.addAttribute("title","成功");
                model.addAttribute("name","新密码:"+new_pass);//
                model.addAttribute("note","密码已被系统重置，请即使登录修改你的新密码");
            } else {
                model.addAttribute("title","抱歉");
                model.addAttribute("name","为获取到用户信息");
                model.addAttribute("note","操作失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("title","抱歉");
            model.addAttribute("name","系统操作过程中发生错误");
            model.addAttribute("note","操作失败");
        }
        model.addAttribute("webhost", SubjectFilter.WEBHOST);
        return "msg";
    }



}
