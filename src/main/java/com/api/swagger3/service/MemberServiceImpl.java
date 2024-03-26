package com.api.swagger3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.api.swagger3.model.dto.QMemberDTO;
import com.api.swagger3.model.request.MemberSerchCondition;
import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.Entity.QMember;
import com.api.swagger3.model.dto.MemberDTO;
import com.api.swagger3.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    
    private final JPAQueryFactory jpaQueryFactory;

    private final MemberRepository memberRepository;

    private final QMember qMember = QMember.member;

    private StringUtils utils;

    @Transactional
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

            memberDTO = jpaQueryFactory.select(
                new QMemberDTO(
                    qMember.memberKey, 
                    qMember.memberId, 
                    qMember.name, 
                    qMember.team.teamName
                    )
                )
                .from(qMember)
                //.innerJoin(qMember.team, qTeam)
                //.fetchJoin()  
                // 2024-03-20 
                // error log : Query specified join fetching, but the owner of the fetched association was not present in the select list [SqmSingularJoin(com.api.swagger3.model.Entity.Member(member1).team(team) : team)]
                // 조인 문제로 인해 해결방법이 fetchJoin() 제거 하여 해결하였으나 N+1문제가 발생할수 있다고 한다.
                .where(qMember.memberId.eq(id))
                .fetchFirst();

                return memberDTO;
        }catch(Exception e){
            log.error("loginMember", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }

    @Transactional
    @Override
    public void setMember(MemberDTO memberDTO) throws Exception {
        try{
            Member m = memberDTO.toEntity();
            log.info("setMember start -------------------");
            log.info(m.toString());
            memberRepository.save(m);
            log.info("setMember end -------------------");
        }catch(Exception e){
            log.error("setMember", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }

    @SuppressWarnings("static-access")
    @Override
    public Page<MemberDTO> setMembersPage(MemberSerchCondition condition, Pageable pageable) throws Exception {
        List<MemberDTO> memberDTO = null;
        try{
            BooleanBuilder builder = new BooleanBuilder();
            if (utils.hasText(condition.getName())) {
                builder.and(qMember.name.eq(condition.getName()));
            }

            if(utils.hasText(condition.getSex())){
                builder.and(qMember.sex.eq(condition.getSex()));
            }

            if(utils.hasText(condition.getType())) {
                builder.and(qMember.type.goe(condition.getType()));
            }

            memberDTO = jpaQueryFactory.select(
                new QMemberDTO(
                    qMember.memberKey, 
                    qMember.memberId, 
                    qMember.name, 
                    qMember.team.teamName
                    )
                )
                .from(qMember)
                .where(builder)
                .orderBy(qMember.memberKey.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();

            JPQLQuery<Long> count = jpaQueryFactory.select(qMember.count()).from(qMember).where(builder);

            return PageableExecutionUtils.getPage(memberDTO, pageable, count::fetchCount);
        }catch(Exception e){
            log.error("loginMember", e);
            throw new Exception("SERVICE ERROR");
        }
    }

}
