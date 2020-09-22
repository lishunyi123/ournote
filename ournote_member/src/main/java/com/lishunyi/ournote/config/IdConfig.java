package com.lishunyi.ournote.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/22 17:04
 **/
@Configuration
public class IdConfig {

    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }
}
