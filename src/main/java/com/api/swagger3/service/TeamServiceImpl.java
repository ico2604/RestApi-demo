package com.api.swagger3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.swagger3.dto.QTeamDTO;
import com.api.swagger3.dto.TeamDTO;
import com.api.swagger3.model.Entity.QMember;
import com.api.swagger3.model.Entity.QTeam;
import com.api.swagger3.model.Entity.Team;
import com.api.swagger3.repository.TeamRepository;
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
    public List<TeamDTO> getTeamList() throws Exception {
        try{
            QTeam qTeam = QTeam.team;
            //QMember qMember = QMember.member;

            List<TeamDTO> teamList = jpaQueryFactory.select(
                    new QTeamDTO(qTeam.teamKey, 
                        qTeam.teamName,
                        qTeam.members
                    )
                )
                .from(qTeam)
                //.leftJoin(qTeam.members, qMember)//.fetchJoin()
                .fetch();
            for(TeamDTO dto : teamList){
                log.info(dto+"");
            }
            
            return teamList;
        }catch(Exception e){
            log.error("getTeamList err", e);
            throw new Exception("SERVICE ERROR");
        }
        
    }
}
