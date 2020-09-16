package com.lishunyi.config;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

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
