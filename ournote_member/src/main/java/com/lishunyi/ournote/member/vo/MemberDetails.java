package com.lishunyi.ournote.member.vo;

import com.lishunyi.ournote.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MemberDetails extends Member implements UserDetails {

    public MemberDetails(Member member) {
        BeanUtils.copyProperties(member, new MemberDetails());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 用户权限赋值
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.getEnabled();
    }
}
