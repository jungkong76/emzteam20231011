package com.class302.omzteam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.class302.omzteam.member.model.MemberDto;
import com.class302.omzteam.mybatis.LoginDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginDao loginDao; // 사용자 정보를 데이터베이스에서 가져오는 DAO

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("사용자 아이디를 입력하세요.");
        }

        MemberDto member = loginDao.getLoginId(Long.parseLong(username));
        System.out.println(member);

        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        // 사용자 정보로 UserDetails 객체 생성
        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getMem_no().toString()) // 아이디 설정
                .password(member.getMem_pw()) // 암호화된 비밀번호 설정
                .roles("USER") // 사용자 역할 설정
                .build();
    }
}
