package com.api.swagger3.model.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.querydsl.core.annotations.QueryProjection;

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
public class LoginDTO {
    @Schema(description = "발급된 엑세스 토큰")
    private String accessToken;

    @Schema(description = "발급된 리프레시 토큰")
    private String refreshToken;

    @Schema(description = "회원코드")
    private Long memberKey;

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

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDateTime regDate;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDateTime modDate;

    @Schema(description = "팀 코드")
    private Long teamKey;

    @Schema(description = "팀 이름")
    private String teamName;

    @QueryProjection
    public LoginDTO(Long memberKey, String memberPw){
        this.memberKey = memberKey;
        this.memberPw = memberPw;
    }

    @QueryProjection
    public LoginDTO(Long memberKey, String memberId, String name, String type, String email,
    String sex, String birthDate, String phoneNumber, Long teamKey, String teamName){
        this.memberKey = memberKey;
        this.memberId = memberId;
        this.name = name;
        this.type = type;
        this.email = email;
        this.sex = sex;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.teamKey = teamKey;
        this.teamName = teamName;
    }
}
