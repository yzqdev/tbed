package cn.hellohao.auth.token;

import cn.hellohao.entity.SysUser;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2021/6/11 18:50
 */
public class JWTUtil {

    private static String EXPIRE_TIME = "";
    private static String SECRET = "www.hellohao.cn";

    public static String createToken(SysUser sysUser){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,604800 );//单位秒，604800 为7天
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("email", sysUser.getEmail())
                .withClaim("username", sysUser.getUsername())
                .withClaim("uid", sysUser.getUid())
                .withClaim("password", sysUser.getPassword())
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static JSONObject checkToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();//验证对象
        JSONObject jsonObject = new JSONObject();
        if(null==token){
            jsonObject.put("check",false);
            return jsonObject;
        }
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            Date expiresAt = verify.getExpiresAt();
            jsonObject.put("check",true);
            jsonObject.put("email",verify.getClaim("email").asString());
            jsonObject.put("password",verify.getClaim("password").asString());
            jsonObject.put("uid",verify.getClaim("uid").asString());
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            System.out.println("token认证已过期，请重新登录获取");
            jsonObject.put("check",false);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("token无效");
            jsonObject.put("check",false);
        }
        return jsonObject;
    }



}
