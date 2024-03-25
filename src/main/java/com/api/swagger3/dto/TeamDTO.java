package com.api.swagger3.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.Entity.Team;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@Data
public class TeamDTO {
    @Schema(description = "팀 코드")
    private Long teamKey;
    @Schema(description = "팀 이름")
    private String teamName;

    private List<Member> members = new ArrayList<>(); 
 
    @QueryProjection
    public TeamDTO(Long teamKey, String teamName, List<Member> members){
        this.teamKey = teamKey;
        this.teamName = teamName;
        this.members = members;
    }

    //dto로 받은 객체를 entity 화하여 저장하는 용도
    public Team toEntity(){
        LocalDateTime now = LocalDateTime.now();
        return Team.builder()
            .teamKey(teamKey)
            .teamName(teamName)
            .modDate(now)
            .build();
    }

}
