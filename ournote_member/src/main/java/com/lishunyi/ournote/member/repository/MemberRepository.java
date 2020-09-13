package com.lishunyi.ournote.member.repository;

import com.lishunyi.ournote.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    /**
     * 根据用户名、邮箱、手机号查询用户
     *
     * @param username 用户名
     * @param email    邮箱
     * @param phone    手机号
     * @return 用户信息
     */
    Optional<Member> findByUsernameOrEmailOrPhone(String username, String email, String phone);
}
