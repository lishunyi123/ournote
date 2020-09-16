package com.lishunyi.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/16 14:49
 **/
@Data
@ConfigurationProperties(prefix = "ignore.url")
public class IgnoreConfig {

    /**
     * 需要忽略的 URL 格式，不考虑请求方法
     */
    private List<String> pattern = Lists.newArrayList();

    /**
     * 需要忽略的 GET 请求
     */
    private List<String> get = Lists.newArrayList();

    /**
     * 需要忽略的 POST 请求
     */
    private List<String> post = Lists.newArrayList();

    /**
     * 需要忽略的 DELETE 请求
     */
    private List<String> delete = Lists.newArrayList();

    /**
     * 需要忽略的 PUT 请求
     */
    private List<String> put = Lists.newArrayList();

    /**
     * 需要忽略的 HEAD 请求
     */
    private List<String> head = Lists.newArrayList();

    /**
     * 需要忽略的 PATCH 请求
     */
    private List<String> patch = Lists.newArrayList();

    /**
     * 需要忽略的 OPTIONS 请求
     */
    private List<String> options = Lists.newArrayList();

    /**
     * 需要忽略的 TRACE 请求
     */
    private List<String> trace = Lists.newArrayList();

}
