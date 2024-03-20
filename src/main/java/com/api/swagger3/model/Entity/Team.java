package com.api.swagger3.model.Entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teamKey")
    private Long teamKey;

	@Column(name = "teamName")
	private String teamName;

	@Column(name = "regDate")@DateTimeFormat(pattern = "yyyyMMdd")
	private LocalDateTime regDate;

	@Column(name = "modDate")@DateTimeFormat(pattern = "yyyyMMdd")
	private LocalDateTime modDate;
}
