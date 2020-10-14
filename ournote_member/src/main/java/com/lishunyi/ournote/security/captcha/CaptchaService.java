package com.lishunyi.ournote.security.captcha;

/**
 * 验证码服务
 *
 * @author 李顺仪
 * @version 1.0
 * @since 2020/10/12 11:32
 **/
public interface CaptchaService {

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return 是否成功
     */
    boolean sendCaptcha(String phone);

    /**
     * 校验验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 是否成功
     */
    boolean verifyCaptcha(String phone, String code);
}
