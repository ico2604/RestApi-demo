package com.api.swagger3.service;

import org.springframework.stereotype.Service;

import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService{

    private final MemberRepository memberRepository;

    public Member loginMember(String id, String pw) throws Exception {
        // TODO Auto-generated method stub
        return memberRepository.loginMember(id, pw);
    }

    public void setMember() throws Exception {
        // TODO Auto-generated method stub
        
    }

}
