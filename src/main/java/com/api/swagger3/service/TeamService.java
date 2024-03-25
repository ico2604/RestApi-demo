package com.api.swagger3.service;

import java.util.List;

import com.api.swagger3.dto.TeamDTO;

public interface TeamService {
    public void setTeam(TeamDTO teamDTO) throws Exception;
    public List<TeamDTO> getTeamList() throws Exception;
} 
