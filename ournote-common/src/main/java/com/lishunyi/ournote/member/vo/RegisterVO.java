package com.lishunyi.ournote.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/22 16:20
 **/
@Data
@ApiModel(description = "注册信息")
public class RegisterVO {

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不可为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @Size(min = 6, max = 18, message = "密码必须在6~18位之间")
    private String password;

    @ApiModelProperty(value = "确认密码", required = true)
    @Size(min = 6, max = 18, message = "密码必须在6~18位之间")
    private String confirmPassword;

    @ApiModelProperty(value = "邮箱")
    @Email
    private String email;

    @ApiModelProperty(value = "手机号", required = true)
    @Pattern(regexp = "^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0125689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[13589]\\d)\\d{7}$"
            , message = "手机号码不正确")
    private String phone;
}
