package com.api.swagger3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.dto.MemberDTO;
import com.api.swagger3.model.dto.TeamDTO;
import com.api.swagger3.model.request.MemberSerchCondition;

public interface MemberService {
    
    public MemberDTO loginMember(String id, String pw) throws Exception;
    public void setMember(MemberDTO memberDTO) throws Exception;
    public Page<MemberDTO> setMembersPage(MemberSerchCondition condition, Pageable pageable) throws Exception;
} 
