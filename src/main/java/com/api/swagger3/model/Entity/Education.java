package com.api.swagger3.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
@Entity
public class Education {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "eduKey")
    private Long eduKey;

	@Column(name = "eduName")
	private String eduName;

	@Column(name = "createDate")
	private String createDate;

	@ManyToOne @JoinColumn(name = "scoreKey")
	private Score score;
}
