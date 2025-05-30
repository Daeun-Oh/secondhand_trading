package org.koreait.member;

import lombok.Builder;
import lombok.Data;
import org.koreait.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Member member;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 권한 설정 - 인가 통제
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 회원 탈퇴 여부
    @Override
    public boolean isEnabled() {
        return member.getDeletedAt() == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
