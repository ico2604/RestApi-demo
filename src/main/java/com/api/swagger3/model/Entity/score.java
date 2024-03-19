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
public class Score {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scoreKey")
    private Long scoreKey;

	@Column(name = "score")
	private int score;
	
	@Column(name = "createDate")
	private String createDate;

	@ManyToOne @JoinColumn(name = "eduKey")
	private Education education;

	@ManyToOne @JoinColumn(name = "memberKey")
	private Member member;
}
