package com.api.swagger3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.api.swagger3.model.dto.QMemberDTO;
import com.api.swagger3.model.request.LoginRequest;
import com.api.swagger3.model.request.MemberSerchCondition;
import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.Entity.QMember;
import com.api.swagger3.model.dto.LoginDTO;
import com.api.swagger3.model.dto.MemberDTO;
import com.api.swagger3.model.dto.MemberSaveDTO;
import com.api.swagger3.model.dto.QLoginDTO;
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

    public static final String PATTERN_ID = "[A-Za-z[0-9]]{6,12}$"; // 영문, 숫자 6~12자리
    public static final String PATTERN_PW = "^(?=.*\\d)(?=.*[~`!?@#$%^&*])(?=.*[a-z]).{8,20}$"; // 영문, 숫자, 특수문자 포함 8~20자
    public static final String PATTERN_EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; // 이메일 형식 확인

    @Transactional
    @Override
    public LoginDTO loginMember(LoginRequest loginRequest) throws Exception {
        log.info("loginMember DTO---------------------------------------");
        LoginDTO checkLoginDTO = null;
        LoginDTO resultLoginDTO = null;
        String id = loginRequest.getId();
        String pw = loginRequest.getPw();
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
            if(!e.getMessage().isEmpty()){
                throw new Exception(e.getMessage());
            }
            throw new Exception("SERVICE ERROR");
        }
        //아이디 존재 확인
        try{
            checkLoginDTO = jpaQueryFactory.select(
                new QLoginDTO(
                    qMember.memberKey, 
                    qMember.memberPw
                    )
                )
                .from(qMember)
                .where(qMember.memberId.eq(id))
                .fetchFirst();

            //없는 계정입니다.
            if(checkLoginDTO == null){
                throw new Exception("NORESULT");
            }
            //클라이언트가 입력한 비밀번호를 sha256 암호화 하여 DB에 저장되어 있는 비밀번호화 비교한다.
            if(!sha256(pw).equals(checkLoginDTO.getMemberPw())){
                throw new Exception("NORESULT");
            }
        }catch(Exception e){
            log.error("Member Check", e);
            throw new Exception("SERVICE ERROR");
        }

        //회원정보 가져오기
        try{
            resultLoginDTO = jpaQueryFactory.select(
                new QLoginDTO(
                    qMember.memberKey, 
                    qMember.memberId, 
                    qMember.name, 
                    qMember.type,
                    qMember.email,
                    qMember.sex,
                    qMember.birthDate,
                    qMember.phoneNumber,
                    qMember.team.teamKey,
                    qMember.team.teamName
                    )
                )
                .from(qMember)
                .where(qMember.memberKey.eq(checkLoginDTO.getMemberKey()))
                .fetchFirst();
                return resultLoginDTO;
        }catch(Exception e){
            log.error("getMember", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }

    @Transactional
    @Override
    public void setMember(MemberSaveDTO memberSaveDTO) throws Exception {
        try{
            Member m = memberSaveDTO.toEntity();
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
                    qMember.type,
                    qMember.email,
                    qMember.sex,
                    qMember.birthDate,
                    qMember.phoneNumber,
                    qMember.team.teamKey,
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
