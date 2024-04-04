package com.api.swagger3.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.request.CustomUserDetails;
import com.api.swagger3.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String key) throws UsernameNotFoundException {
        Member m = memberRepository.findById(Long.parseLong(key))
            .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));
        return new CustomUserDetails(m.getMemberKey(), m.getMemberPw(), m.getPhoneNumber(), true, false);
    }

}
