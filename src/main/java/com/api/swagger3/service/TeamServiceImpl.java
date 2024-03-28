package com.api.swagger3.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.api.swagger3.model.Entity.QMember;
import com.api.swagger3.model.Entity.QTeam;
import com.api.swagger3.model.Entity.Team;
import com.api.swagger3.model.dto.QTeamSelectDTO;
import com.api.swagger3.model.dto.TeamDTO;
import com.api.swagger3.model.dto.TeamSelectDTO;
import com.api.swagger3.repository.TeamRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    
    private final JPAQueryFactory jpaQueryFactory;

    private final TeamRepository teamRepository;

    private final EntityManager entityManager;

    private QTeam qTeam = QTeam.team;
    private QMember qMember = QMember.member;
    
    @Transactional
    @Override
    public void setTeam1(TeamDTO teamDTO) throws Exception {
        try{
            Team m = teamDTO.toEntity();
            /**
             * #JPA
             * persist()는 리턴값이 없는 insert
             * merge()는 리턴값이 없는 update
             * save()는 리턴값이 있는 insert update
             * save는 entity에서 새로운 entity면 persist() 아니면 merge()를 호출한다.
             * merge는 한번 persist상태였다가 detached된 상태에서 그 다음 persist상태가 될 때, merge라고 한다.
             */ 

            teamRepository.save(m);
        }catch(Exception e){
            log.error("setTeamJPA", e);
            throw new Exception("SERVICE ERROR");
        }
    }
    //QueryDSL은 INSERT를 자체적으로 제공하고 있지 않아서 EntityManager을 이용해야 한다.
    @Transactional
    @Override
    public void setTeam2(TeamDTO teamDTO) throws Exception {
        try{
            entityManager.createNativeQuery(
                "INSERT INTO team (team_name) " +
                "VALUES (?)"
                )
                .setParameter(1, teamDTO.getTeamName())
                .executeUpdate();
        }catch(Exception e){
            log.error("setTeamQueryDSL", e);
            throw new Exception("SERVICE ERROR");
        }
    }

    @Transactional
    @Override
    public List<TeamSelectDTO> getTeamList() throws Exception {
        try{
            List<TeamSelectDTO> teamList = jpaQueryFactory
                .select(
                    new QTeamSelectDTO(qTeam.teamKey, qTeam.teamName, qMember.memberKey.count().as("memberCount"))
                )
                .from(qTeam)
                //.where(qMember.sex.eq("1"))
                .groupBy(qTeam.teamKey)
                .orderBy(qTeam.teamKey.desc())
                .leftJoin(qMember).on(qTeam.teamKey.eq(qMember.team.teamKey)).fetchJoin()
                .fetch();
            for(TeamSelectDTO t : teamList){
                System.out.println(t);
            }
            return teamList;
        }catch(Exception e){
            log.error("getTeamList err", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }
    
    @Transactional
    @Override
    public void updateTeam1(TeamDTO teamDTO) throws Exception {
        try{
            //첫번째 방법은 Dirty Checking - 실시간 비즈니스 처리, 실시간 단건 처리
            //Team team = teamRepository.findById(teamDTO.getTeamKey()).orElseThrow(() -> new IllegalAccessException("team not found"));
            // team.setTeamName(teamDTO.getTeamName());
            // team.setModDate(LocalDateTime.now());

            //두번째 방법은 벌크 쿼리 Querydsl.update - 대량의 데이터를 일괄로 Update 처리 시
            Long res = jpaQueryFactory.update(qTeam)
                .where(qTeam.teamKey.eq(teamDTO.getTeamKey()))
                .set(qTeam.teamName, teamDTO.getTeamName())
                .set(qTeam.modDate, LocalDateTime.now())
                .execute();
            entityManager.flush();
            entityManager.clear();
            //세번째로 Repository를 이용한 save가 있지만, 불필요한 칼럼도 업데이트를 하게 되어 성능상 패스! 
        }catch(Exception e){
            log.error("updateTeamJPA err", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }

    @Transactional
    @Override
    public void removeTeam(Long teamKey) throws Exception {
        try{
            jpaQueryFactory.delete(qTeam).where(qTeam.teamKey.eq(teamKey)).execute();
            entityManager.flush();
            entityManager.clear();
        }catch(Exception e){
            log.error("removeTeamJPA err", e);
            throw new Exception("SERVICE ERROR");
        }
    }
    
}
