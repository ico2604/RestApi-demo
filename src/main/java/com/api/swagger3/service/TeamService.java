package com.api.swagger3.service;

import java.util.List;

import com.api.swagger3.model.dto.TeamDTO;
import com.api.swagger3.model.dto.TeamSelectDTO;

public interface TeamService {
    public void setTeam(TeamDTO teamDTO) throws Exception;
    public List<TeamSelectDTO> getTeamList() throws Exception;
} 
