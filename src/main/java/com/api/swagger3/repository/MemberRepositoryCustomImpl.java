package com.api.swagger3.repository;

import java.util.List;
import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.Entity.QMember;
import com.api.swagger3.model.Entity.QTeam;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member loginMember(String id, String pw) {
        QMember member = QMember.member;
        QTeam team = QTeam.team;

        Member m = jpaQueryFactory.select(member)
                            .from(member)
                            .leftJoin(member.team, team).fetchJoin()
                            .where(member.memberId.eq(id))
                            .fetchFirst();
        return m;
    }

    @Override
    public void joinMember(Member member) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinMember'");
    }

}
