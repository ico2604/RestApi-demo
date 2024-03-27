package com.api.swagger3.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    
    private final JPAQueryFactory jpaQueryFactory;

    private final TeamRepository teamRepository;

    private final QTeam qTeam = QTeam.team;
    private final QMember qMember = QMember.member;
    
    @Transactional
    @Override
    public void setTeam(TeamDTO teamDTO) throws Exception {
        try{
            Team m = teamDTO.toEntity();
            log.info("setTeam start -------------------");
            log.info(m.toString());
            teamRepository.save(m);
            log.info("setTeam end -------------------");
        }catch(Exception e){
            log.error("setTeam", e);
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

    @Override
    public void updateTeam(TeamDTO teamDTO) throws Exception {
        try{
            Team team = teamRepository.findById(teamDTO.getTeamKey()).orElseThrow(() -> new IllegalAccessException("team not found"));

            //Dirty Checking
            team.setTeamName(teamDTO.getTeamName());
            team.setModDate(LocalDateTime.now());
        }catch(Exception e){
            log.error("updateTeam err", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }
}
