package com.lishunyi.ournote.security.captcha;

/**
 * 验证码缓存生命周期
 *
 * @author 李顺仪
 * @version 1.0
 * @since 2020/10/11 15:18
 **/
public interface CaptchaCacheStorage {

    /**
     * 验证码放进缓存
     *
     * @param phone 手机号
     * @return 验证码
     */
    String put(String phone);

    /**
     * 从缓存获取验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    String get(String phone);

    /**
     * 验证码手动过期
     *
     * @param phone 手机号
     */
    void expire(String phone);
}
