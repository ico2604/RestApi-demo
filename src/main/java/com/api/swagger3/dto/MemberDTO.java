package com.api.swagger3.dto;

import java.time.LocalDateTime;

import com.api.swagger3.model.Entity.Team;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {
    private Long memberKey;
    private String memberId;
    private String memberPw;
    private String name;
    private String type;
    private String email;
    private String sex;
    private String birthDate;
    private String phoneNumber;
    private LocalDateTime regDate;
    private LocalDateTime modDate;


    private Team team;
    private Long teamKey;
    private String teamName;

    @QueryProjection
    public MemberDTO(Long memberKey, String memberId, String name, String teamName){
        this.memberKey = memberKey;
        this.memberId = memberId;
        this.name = name;
        this.teamName = teamName;
    }
}
