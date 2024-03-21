package com.api.swagger3.model.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberKey;

    private String memberId;

    private String memberPw;

	private String name;

	private String type;

	@Email
	private String email;

	private String sex;

	private String birthDate;

	private String phoneNumber;

	@CreatedDate
	private LocalDateTime regDate;

	private LocalDateTime modDate;

	//EAGER(즉시로딩), LAZY(지연로딩)으로 즉시로딩은 조회시 자동으로 조인 칼럼과 조인을 하며, 지연로딩은 자동으로 조인시키지 않는다. 
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "teamKey")
	private Team team;
}
