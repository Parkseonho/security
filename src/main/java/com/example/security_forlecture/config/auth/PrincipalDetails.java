package com.example.security_forlecture.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인이 진행이 완료가 되면 시큐리티 session을 만들어준다.
// 시큐리티는 자신만의 고유한 세션 공간을 가짐. (Security ContextHolder)라는 키값에 세션정보를 저장시킴
// 시큐리티가 가지고있는 공간에 들어갈 수 있는 오브젝트가 정해져있음.
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User정보가 있어야 됨.
// User 오브젝트의 타입은 => UserDetails 타입 객체

// Session => Security Session => Authentication => UserDetails(PrincipalDetails)
// UserDetails를 꺼내면 User정보에 접근할 수 있음

import com.example.security_forlecture.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
        //로그인 폼에서 입력된 user객체
    }

    // 해당 User의 권한을 리턴하는곳
    // User의 롤을 긁어온다?
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override // 암호화된 패스워드를 가져온다.
    public String getPassword() {
        return user.getPassword();
    }

    @Override // 중복이 안되는 고유한값이어야함
    public String getUsername() {
        return user.getUsername();
    }

    @Override // User가 만료된것인지 아닌지 확인
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 마지막 로그인을 확인하여 활성화 상태를 구분할 수 있음
    public boolean isEnabled() {
        return true;
    }

}
