package com.lishunyi.ournote.member.controller;

import com.lishunyi.ournote.member.service.IMemberService;
import com.lishunyi.ournote.member.vo.LoginVO;
import com.lishunyi.ournote.member.vo.RegisterVO;
import com.lishunyi.ournote.security.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @ApiOperation(value = "登录API")
    public String login(@RequestBody LoginVO loginVO) {
        System.out.println(1);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVO.getUsernameOrEmailOrPhone(), loginVO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createJWT(authentication, loginVO.getRememberMe());
        return jwt;
    }

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
