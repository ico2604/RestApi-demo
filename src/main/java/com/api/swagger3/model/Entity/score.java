package com.api.swagger3.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class score {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scoreKey")
    private Long scoreKey;

	@Column(name = "score")
	private int score;

	@Column(name = "memberKey")
	private Long memberKey;

	@Column(name = "birthDate")
	private String birthDate;
}
