package com.lishunyi.ournote.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.lishunyi.ournote.member.vo.MemberDetails;
import com.lishunyi.ournote.security.config.JwtConfig;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/14 14:11
 **/
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String createJWT(Boolean rememberMe, Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .claim("roles", roles)
                .claim("authorities", authorities);

        // 过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        stringRedisTemplate.opsForValue().set("jwt:" + subject, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    public String createJWT(Authentication authentication, Boolean rememberMe) {
        MemberDetails principal = (MemberDetails) authentication.getPrincipal();
        return createJWT(rememberMe, principal.getId(), principal.getUsername(), null, principal.getAuthorities());
    }

    public Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt).getBody();

            String username = claims.getSubject();
            String redisKey = "jwt:" + username;

            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                // 抛异常
                throw new NullPointerException("token不存在");
            }

            // 如果jwt与redis中的不想等，均表示已过期
            String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new NullPointerException("token已过期");
            }
            return claims;
        } catch (ExpiredJwtException e) {
            throw new NullPointerException("token已过期");
        } catch (UnsupportedJwtException e) {
            throw new NullPointerException("不支持的token");
        } catch (MalformedJwtException e) {
            throw new NullPointerException("token无效");
        } catch (SignatureException e) {
            throw new NullPointerException("无效的签名");
        } catch (IllegalArgumentException e) {
            throw new NullPointerException("token不存在");
        }
    }

    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJWTFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        stringRedisTemplate.delete("jwt:" + username);
    }

    /**
     * 从cookie、请求头、参数中获取JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJWTFromRequest(HttpServletRequest request) {
        String authorization = null;
        try {
            // 先从cookie获取
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (StrUtil.equals(cookie.getName(), "Authorization")) {
                    authorization = cookie.getValue();
                }
            }
            // 没有就从头部获取
            if (StrUtil.isEmpty(authorization)) {
                authorization = request.getHeader("Authorization");
                if (StrUtil.isEmpty(authorization)) {
                    // 没有就从参数获取
                    authorization = request.getParameter("Authorization");
                }
            }

            if (StrUtil.isNotEmpty(authorization)) {
                return authorization.substring(7);
            }
        } catch (Exception e) {
            // 获取jwt失败
        }
        return null;
    }

    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }
}
