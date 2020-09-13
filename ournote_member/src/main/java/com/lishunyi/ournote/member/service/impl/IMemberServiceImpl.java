package com.lishunyi.ournote.member.service.impl;

import com.lishunyi.ournote.member.repository.MemberRepository;
import com.lishunyi.ournote.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private MemberRepository memberRepository;
}
