package com.lishunyi.ournote.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/14 14:00
 **/
@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfig {

    private String key = "lishunyi";

    private Long ttl = 600000L;

    private Long remember = 604800000L;
}
