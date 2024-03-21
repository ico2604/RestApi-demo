package com.api.swagger3.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamDTO {
    @Schema(description = "팀 코드")
    private Long teamKey;
    @Schema(description = "팀 이름")
    private String teamName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
