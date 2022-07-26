package com.example.demo.commons.configs.security.utils;

import com.example.demo.commons.configs.security.domain.Role;
import com.example.demo.commons.configs.security.domain.UserInfo;
import com.example.demo.commons.configs.security.enums.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liwenji
 * @ClassName JwtUtils
 * @Description TODO，Jwt 工具类，用于生成、解析与验证 token
 * @date 2022/5/27 18:51
 * @Version 1.0
 */
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 签名的密钥
     */
    private static final byte[] SECRET_KEY = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private JwtUtils() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    /**
     * 根据用户名和用户角色生成 token
     *
     * @param userInfo 用户信息
     * @param isRemember 是否记住我
     * @return 返回生成的 token
     */
    public static String generateToken(UserInfo userInfo, boolean isRemember) {
        // 过期时间
        long expiration = isRemember ? SecurityConstants.EXPIRATION_REMEMBER_TIME : SecurityConstants.EXPIRATION_TIME;
        // 生成 token
        String token = Jwts.builder()

                // 生成签证信息
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY), SignatureAlgorithm.HS256)
                // 主题，代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(String.valueOf (userInfo.getUserId ()))
//                .setId(id)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性 token,从而回避重放攻击。
                // 自定义属性
                .claim(SecurityConstants.TOKEN_ROLE_CLAIM, userInfo.getRoles ())
                // 签发者
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                // 签发时间
                .setIssuedAt(new Date ())
                // 接受者
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                // 设置有效时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();

        return token;
    }

    /**
     * 验证 token 是否有效
     *
     * <p>
     * 如果解析失败，说明 token 是无效的
     *
     * @param token token 信息
     * @return 如果返回 true，说明 token 有效
     */
    public static boolean validateToken(String token) {
        try {
            getTokenBody(token);


            return true;
        } catch ( ExpiredJwtException e) {
            logger.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch ( UnsupportedJwtException e) {
            logger.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch ( MalformedJwtException e) {
            logger.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }

    /**
     * 根据 token 获取用户认证信息
     *
     * @param token token 信息
     * @return 返回用户认证信息
     */
    public static Authentication getAuthentication(String token) {
        //token 解析
        Claims claims = getTokenBody(token);
        // 获取用户角色字符串
        List<Role> roles = (List<Role>) claims.get(SecurityConstants.TOKEN_ROLE_CLAIM);
        List<SimpleGrantedAuthority> authorities =
                Objects.isNull(roles) ? Collections.singletonList(new SimpleGrantedAuthority(SecurityConstants.ROLE_USER)) :
                        roles.stream().map(n -> new SimpleGrantedAuthority(String.valueOf(n.getRoleId()))).collect(Collectors.toList());
        // 获取用户名
        String userName = claims.getSubject();

        return new UsernamePasswordAuthenticationToken (userName, token, authorities);

    }

    /**
     *  解密 JWT的 token
     *
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                //设置签名密匙
                .setSigningKey(SECRET_KEY)
                //解析 token
                .parseClaimsJws(token)
                //获取 签发者、签发时间、过期时间、签名算法及密匙等
                .getBody();
    }

    /**
     * 从 HTTP 请求中的请求头里获取 token
     *
     * @param request HttpServletRequest
     * @return token
     */
    public static String getTokenFromHttpRequest (HttpServletRequest request){
        String token = request.getHeader("token");
        if ( StringUtils.isBlank (token) ) {
            throw new IllegalArgumentException("未登录，请登录后尝试");
        }
        logger.info("获得的token为：{}", token);
        return token;
    }
}
