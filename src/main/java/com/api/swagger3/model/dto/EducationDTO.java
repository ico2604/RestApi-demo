package com.api.swagger3.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EducationDTO {
    private Long eduKey;
    private int eduType;
    private String eduName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
