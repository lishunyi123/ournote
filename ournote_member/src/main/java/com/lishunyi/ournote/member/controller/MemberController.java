package com.lishunyi.ournote.member.controller;

import com.lishunyi.ournote.member.service.IMemberService;
import com.lishunyi.ournote.member.vo.RegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/18 16:53
 **/
@RestController
@RequestMapping("/auth")
@Api(tags = "用户相关--API")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @PostMapping("/register")
    @ApiOperation(value = "注册API")
    public Boolean register(@RequestBody RegisterVO registerVO) {
        return memberService.register(registerVO);
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试")
    public String test() {
        return "123";
    }
}
