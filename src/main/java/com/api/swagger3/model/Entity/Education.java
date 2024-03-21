package com.api.swagger3.model.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class Education extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long eduKey;

    private int eduType;

	private String eduName;

	@CreatedDate
	private LocalDateTime regDate;

	private LocalDateTime modDate;
}
