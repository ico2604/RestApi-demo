package com.api.swagger3.repository;

import com.api.swagger3.dto.MemberDTO;
import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.Entity.QMember;
import com.api.swagger3.model.Entity.QTeam;

import java.util.List;

public interface MemberRepositoryCustom {
    default QMember member(){
        return QMember.member;
    }
    default QTeam team(){
        return QTeam.team;
    }
    MemberDTO loginMember(String id, String pw);
    void setMember(Member member);
}