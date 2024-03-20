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
public class ScoreDTO {
    private Long scoreKey;
    private int score;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private Long eduKey;
    private Long memberKey;
}
