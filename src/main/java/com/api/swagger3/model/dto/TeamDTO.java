package com.api.swagger3.model.dto;

import java.time.LocalDateTime;

import com.api.swagger3.model.Entity.Team;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeamDTO {
    @Schema(description = "팀 코드")
    private Long teamKey;
    @Schema(description = "팀 이름")
    private String teamName;

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
