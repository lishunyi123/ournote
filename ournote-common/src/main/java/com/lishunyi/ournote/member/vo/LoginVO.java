package com.lishunyi.ournote.member.vo;

import lombok.Data;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/18 17:12
 **/
@Data
public class LoginVO {

    private String usernameOrEmailOrPhone;

    private String password;

    private Boolean rememberMe = false;
}
