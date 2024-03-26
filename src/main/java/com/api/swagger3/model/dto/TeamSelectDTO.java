package com.api.swagger3.model.dto;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeamSelectDTO {
    @Schema(description = "팀 코드")
    private Long teamKey;
    @Schema(description = "팀 이름")
    private String teamName;
    @Schema(description = "회원수")
    private Long memberCount;

    @QueryProjection
    public TeamSelectDTO(Long teamKey, String teamName, Long memberCount){
        this.teamKey = teamKey;
        this.teamName = teamName;
        this.memberCount = memberCount;
    }
}
