package com.api.swagger3.model;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "User description")
public class User {
    @Id
    @Schema(description = "회원코드")
    private Long id;

    @Schema(description = "아이디")
    private String userId;

    @Schema(description = "이름")
	private String name;

    @Pattern(regexp = "[일반:0, 네이버:1, 카카오:2]")
	@Schema(description = "유형", defaultValue = "0", allowableValues = {"0", "1", "2"})
	private String type;

    @Email
	@Schema(description = "이메일", nullable = false, example = "abc@jiniworld.me")
	private String email;

    @Pattern(regexp = "[남:1-여:2]")
	@Schema(description = "성별", defaultValue = "1", allowableValues = {"1", "2"})
	private String sex;

    @DateTimeFormat(pattern = "yyMMdd")
	@Schema(description = "생년월일", example = "yyMMdd", maxLength = 6)
	private String birthDate;

	@Schema(description = "전화번호", example = "01012345678")
	private String phoneNumber;

    
    private void setbirthDate(String birthDate){
        this.birthDate = birthDate.replaceAll("\\-", "");
    }

    private void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber.replaceAll("\\-", "");
    }

}
