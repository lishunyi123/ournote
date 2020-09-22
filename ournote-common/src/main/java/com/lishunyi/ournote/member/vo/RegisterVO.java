package com.lishunyi.ournote.member.vo;

import lombok.Data;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/22 16:20
 **/
@Data
public class RegisterVO {

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private String phone;
}
