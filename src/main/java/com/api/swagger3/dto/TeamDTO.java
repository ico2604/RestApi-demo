package com.api.swagger3.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamDTO {
    private Long teamKey;
    private String teamName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
