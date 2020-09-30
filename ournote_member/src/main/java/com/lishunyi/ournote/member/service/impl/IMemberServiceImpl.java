package com.lishunyi.ournote.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.lishunyi.base.exception.BusinessException;
import com.lishunyi.ournote.config.IdConfig;
import com.lishunyi.ournote.member.entity.Member;
import com.lishunyi.ournote.member.repository.MemberRepository;
import com.lishunyi.ournote.member.service.IMemberService;
import com.lishunyi.ournote.member.vo.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdConfig idConfig;

    @Override
    public boolean register(RegisterVO registerVO) {
        // 校验两次密码是否一致
        if (!StrUtil.equals(registerVO.getPassword(), registerVO.getConfirmPassword())) {
            throw new BusinessException("密码输入不一致");
        }

        Member member = BeanUtil.copyProperties(registerVO, Member.class);
        member.setId(idConfig.snowflake().nextId());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return true;
    }
}
