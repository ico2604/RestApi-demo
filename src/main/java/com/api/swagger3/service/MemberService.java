package com.api.swagger3.service;

import com.api.swagger3.dto.MemberDTO;
import com.api.swagger3.dto.TeamDTO;
import com.api.swagger3.model.Entity.Member;

public interface MemberService {
    
    public MemberDTO loginMember(String id, String pw) throws Exception;
    public void setMember(MemberDTO memberDTO) throws Exception;
} 
