package com.api.swagger3.service;

import org.springframework.stereotype.Service;

import com.api.swagger3.dto.MemberDTO;
import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    
private final MemberRepository memberRepository;

    @Override
    public MemberDTO loginMember(String id, String pw) throws Exception {
        log.info("loginMember DTO---------------------------------------");

        try {
            // 아이디와 비밀번호 체크(여기에 jwt를 사용하도록 한다)
            if(id.isEmpty()){
                throw new Exception("ID를 입력해주세요.");
            }
            if(pw.isEmpty()){
                throw new Exception("PW를 입력해주세요.");
            }
            if(id.length() < 3){
                throw new Exception("3개 이상의 아이디를 입력해주세요");
            }
            if(pw.length() < 4){
                throw new Exception("4개 이상의 비밀번호를 입력해주세요");
            }
        }catch(Exception e){
            log.error("check", e);
            if(!e.getMessage().isEmpty()){
                throw new Exception(e.getMessage());
            }
            throw new Exception("SERVICE ERROR");
        }
        
        MemberDTO memberDTO = null;
        try{
            memberDTO = memberRepository.loginMember(id, pw);
        }catch(Exception e){
            log.error("loginMember", e);
            throw new Exception("SERVICE ERROR");
        }
        return memberDTO;
    }

    @Override
    public void setMember(MemberDTO memberDTO) throws Exception {
        try{

        }catch(Exception e){
            log.error("setMember", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }
}
