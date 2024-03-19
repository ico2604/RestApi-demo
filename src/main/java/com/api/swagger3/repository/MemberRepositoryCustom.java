package com.api.swagger3.repository;

import com.api.swagger3.model.Entity.Member;
import java.util.List;

public interface MemberRepositoryCustom {
    Member loginMember(String id, String pw);
    void joinMember(Member member);
}