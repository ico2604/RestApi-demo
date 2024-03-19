package com.api.swagger3.model.Entity;

import org.springframework.format.annotation.DateTimeFormat;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberKey") @Schema(description = "회원코드")
    private Long memberKey;

	@Column(name = "memberId") @Schema(description = "아이디")
    private String memberId;

	@Column(name = "memberPw") @Schema(description = "이름")
    private String memberPw;

	
	@Column(name = "name")@Schema(description = "이름")
	private String name;

	@Pattern(regexp = "[일반:0, 네이버:1, 카카오:2]")
	@Column(name = "type") @Schema(description = "유형", defaultValue = "0", allowableValues = {"0", "1", "2"})
	private String type;

	@Email
	@Column(name = "email") @Schema(description = "이메일", nullable = false, example = "abc@jiniworld.me")
	private String email;

	@Pattern(regexp = "[남:1-여:2]")
	@Column(name = "sex")@Schema(description = "성별", defaultValue = "1", allowableValues = {"1", "2"})
	private String sex;

	@DateTimeFormat(pattern = "yyyyMMdd")
	@Column(name = "birthDate")@Schema(description = "생년월일", example = "yyyyMMdd", maxLength = 8)
	private String birthDate;

	@Column(name = "phoneNumber")@Schema(description = "전화번호", example = "01012345678")
	private String phoneNumber;

	@ManyToOne @JoinColumn(name = "teamKey")
	private Team team;

	private void setbirthDate(String birthDate){
        this.birthDate = birthDate.replaceAll("\\-", "");
    }

    private void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber.replaceAll("\\-", "");
    }
}
