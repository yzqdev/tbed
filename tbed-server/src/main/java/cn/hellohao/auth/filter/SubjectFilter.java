package cn.hellohao.auth.filter;

import cn.hellohao.auth.token.JWTUtil;
import cn.hellohao.pojo.User;
import cn.hellohao.service.impl.UserServiceImpl;
import cn.hellohao.utils.SpringContextHolder;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2021/6/16 17:43
 */

public class SubjectFilter extends BasicHttpAuthenticationFilter {

    public static String WEBHOST = null;
    private String CODE ="000";

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        UserServiceImpl userService = SpringContextHolder.getBean(UserServiceImpl.class);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse =(HttpServletResponse) response;
        String serviceName = httpServletRequest.getServletPath();//获取接口
        String Users_Origin = httpServletRequest.getHeader("usersOrigin");

        //验证前端域名
        if(httpServletRequest.getMethod().equals("POST") && !serviceName.contains("/api") && !serviceName.contains("/verifyCode")){
            try{
                if(Users_Origin.compareTo(SecureUtil.md5(WEBHOST))!=0){
                    System.out.println("前端域名校验未通过");
                    System.out.println("request-MD5:"+Users_Origin);
                    System.out.println("配置文件-MD5:"+SecureUtil.md5(WEBHOST));
                    System.out.println("配置Host:"+WEBHOST);
                    this.CODE = "406";
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                this.CODE = "500";
                return false;
            }
        }
        String token = httpServletRequest.getHeader("Authorization");
        JSONObject jsonObject = JWTUtil.checkToken(token);
        if(!jsonObject.getBoolean("check")){
            if(!serviceName.contains("admin")){
                return true;
            }else{
                this.CODE = "403";
                return false;
            }
        }else{
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            if(user==null){
                UsernamePasswordToken tokenOBJ = new UsernamePasswordToken(jsonObject.getString("email"),jsonObject.getString("password"));
                tokenOBJ.setRememberMe(true);
                try {
                    subject.login(tokenOBJ);
                    SecurityUtils.getSubject().getSession().setTimeout(3600000);//一小时
                } catch (Exception e) {
//                    System.err.println("拦截器，登录失败，false");
                    this.CODE = "403";
                    return false;
                }
            }else{
                if(null!=user){
                    try{
                        if(null != user.getId()){
                            if(userService.getUsers(user).getIsok()<1){
                                subject.logout();
                                this.CODE = "403";
                                return false;
                            }
                        }
                    }catch (Exception e){
                        System.out.println("拦截器判断用户isOK的时候报错了");
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)  {
        String info = "未知错误";
        try {
            if(this.CODE.equals("406")){
                info = "前端域名配置不正确";
            }else if(this.CODE.equals("403")){
                info = "当前用户无权访问该请求";
            }else if(this.CODE.equals("402")){
                info = "当前web请求不合规";
            }
            System.err.println("拦截器False-"+info);
            response.setContentType("application/json;charset=UTF-8");
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",this.CODE);
            jsonObject.put("info",info);
            response.getWriter().write(jsonObject.toJSONString());
        } catch (Exception e) {
            System.out.println("返回token验证失败403请求，报异常了");
        }

        return false;
    }

}