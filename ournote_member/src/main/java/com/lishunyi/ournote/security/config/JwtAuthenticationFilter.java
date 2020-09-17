package com.lishunyi.ournote.security.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import com.lishunyi.ournote.member.service.MemberDetailsService;
import com.lishunyi.ournote.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/16 15:34
 **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private IgnoreConfig ignoreConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberDetailsService memberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (checkIgnores((request))) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = jwtUtil.getJWTFromRequest(request);
        if (StrUtil.isNotBlank(jwt)) {
            try {
                String username = jwtUtil.getUsernameFromJWT(jwt);
                UserDetails userDetails = memberDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (SecurityException e) {

            }
        } else {

        }
    }

    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (Objects.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        HashSet<String> ignores = Sets.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(ignoreConfig.getGet());
                break;
            case PUT:
                ignores.addAll(ignoreConfig.getPut());
                break;
            case HEAD:
                ignores.addAll(ignoreConfig.getHead());
                break;
            case POST:
                ignores.addAll(ignoreConfig.getPost());
                break;
            case PATCH:
                ignores.addAll(ignoreConfig.getPatch());
                break;
            case TRACE:
                ignores.addAll(ignoreConfig.getTrace());
                break;
            case DELETE:
                ignores.addAll(ignoreConfig.getDelete());
                break;
            case OPTIONS:
                ignores.addAll(ignoreConfig.getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(ignoreConfig.getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }
}
