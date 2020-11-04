package com.myz.starters.login.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzMa
 * @desc 2020-07-04 16:11:40.492 [http-nio-80-exec-1] INFO  c.h.z.interceptor.WechatLoginInterceptor - method:errorHtml need authorization jsonFlag:false
 * @date 2020/7/4 3:14 PM
 * @email 2641007740@qq.com
 */
public class JWTUtil {

    private static final String SECRET = "!Q@W#E$R";

    private static final String JWT_JSON_BODY_KEY = "dataJson";

    /**
     * @param userJson
     * @param expireSecond
     * @return
     */
    public static String createToken(String userJson, int expireSecond) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, expireSecond);
        Date expireDate = instance.getTime();

        Map<String, Object> jwtHeaderMap = new HashMap<>();
        String token = JWT.create()
                .withHeader(jwtHeaderMap)
                .withClaim(JWT_JSON_BODY_KEY, userJson)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(SECRET.getBytes()));
        return token;
    }


    /**
     * 过期抛出异常 com.auth0.jwt.exceptions.TokenExpiredException: The Token has expired on Sat Jul 04 16:34:11 CST 2020.
     *
     * @param token
     * @return
     */
    public static String verifyToken(String token) throws TokenExpiredException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET.getBytes())).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim userJson = decodedJWT.getClaim(JWT_JSON_BODY_KEY);
        return userJson.asString();
    }

    public static void main(String[] args) {
        String userJson = "{\"id\":1,\"name\":\"zhangsan\"}";
        String token = JWTUtil.createToken(userJson, 1 * 60);
        System.out.println("token2=" + token);
        String s = JWTUtil.verifyToken(token);
        System.out.println("data1=" + s);
        userJson = "{\"id\":1,\"name\":\"zhangsan2\"}";
        token = JWTUtil.createToken(userJson, 1 * 60);
        System.out.println("token2=" + token);
        s = JWTUtil.verifyToken(token);
        System.out.println("data2=" + s);
    }

}
