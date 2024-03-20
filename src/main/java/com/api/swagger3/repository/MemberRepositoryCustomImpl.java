package com.api.swagger3.repository;

import java.util.List;

import com.api.swagger3.dto.MemberDTO;
import com.api.swagger3.dto.QMemberDTO;
import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.Entity.QMember;
import com.api.swagger3.model.Entity.QTeam;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    private QMember qMember = QMember.member;
    private QTeam qTeam = QTeam.team;

    @Override
    public MemberDTO loginMember(String id, String pw) {
        

        MemberDTO dto = jpaQueryFactory.select(
            new QMemberDTO(
                qMember.memberKey, 
                qMember.memberId, 
                qMember.name, 
                qMember.team.teamName
                )
            )
            .from(qMember)
            .innerJoin(qMember.team, qTeam)
            //.fetchJoin()  
            // 2024-03-20 
            // error log : Query specified join fetching, but the owner of the fetched association was not present in the select list [SqmSingularJoin(com.api.swagger3.model.Entity.Member(member1).team(team) : team)]
            // 조인 문제로 인해 해결방법이 fetchJoin() 제거 하여 해결하였으나 N+1문제가 발생할수 있다고 한다.
            .where(qMember.memberId.eq(id))
            .fetchFirst();
        return dto;
    }

    @Override
    public void setMember(Member member) {
        QMember qMember = QMember.member;

        
    }

}
