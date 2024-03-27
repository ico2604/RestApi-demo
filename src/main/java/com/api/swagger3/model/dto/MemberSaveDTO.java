package com.api.swagger3.model.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.Entity.Team;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberSaveDTO {

    @Schema(description = "아이디")
    private String memberId;

    @Schema(description = "패스워드")
    private String memberPw;

    @Schema(description = "이름")
    private String name;

    @Pattern(regexp = "[일반:0, 네이버:1, 카카오:2]")
    @Schema(description = "유형", defaultValue = "0", allowableValues = {"0", "1", "2"})
    private String type;

    @Schema(description = "이메일", nullable = false, example = "abc@jiniworld.me")
    private String email;

    @Pattern(regexp = "[남:1-여:2]")
    @Schema(description = "성별", defaultValue = "1", allowableValues = {"1", "2"})
    private String sex;

    @Schema(description = "생년월일", example = "yyyyMMdd", maxLength = 8)
    private String birthDate;

    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "팀 코드")
    private Long teamKey;

    //dto로 받은 객체를 entity 화하여 저장하는 용도
    public Member toEntity(){
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.builder()
            .memberId(memberId)
            .memberPw(memberPw)
            .name(name)
            .type(type)
            .email(email)
            .sex(sex)
            .birthDate(birthDate)
            .phoneNumber(phoneNumber)
            .modDate(now)
            .build();
        
        if(teamKey != null) {
            member.setTeam(Team.builder().teamKey(teamKey).build());
        }
        
        return member;
        
    }
}
