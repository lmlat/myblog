package com.aitao.myblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Author : AiTao
 * Date : 2020/10/26
 * Time : 10:14
 * Information : token工具类
 */
public class TokenUtil {
    private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);//日志
    private static final long EXPIRE = 1000 * 60 * 60 * 24;//token过期时间
    private static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";//秘钥

    /**
     *
     * @param id
     * @param nickname
     * @return
     */
    public static String getToken(String id, String nickname){
        String token = Jwts.builder()
                //设置头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("user")
                .setIssuedAt(new Date())
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置token主体部分（这里使用id和nickname作为主体部分）
                .claim("id", id)
                .claim("nickname", nickname)
                //加密方式
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return token;
    }

    /**
     * 判断token是否存在与有效（通过APP_SECRET解析token串）
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        //token串为空
        if(StringUtils.isEmpty(token)){
            return false;
        }
        try {
            //通过APP_SECRET解析token串
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            logger.warn("token串解析异常!!!");
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效（通过获取请求头信息获取token再使用APP_SECRET解析token）
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            //获取请求头token
            String token = request.getHeader("token");
            //判断token是否为空
            if(StringUtils.isEmpty(token)){
                return false;
            }
            //通过获取请求头信息获取token再使用APP_SECRET解析token
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            logger.warn("token串解析异常!!!");
            return false;
        }
        return true;
    }

    /**
     * 根据token字符串获取用户id（取出有效载荷中的用户信息）
     * @param request
     * @return
     */
    public static String getUserIdByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
