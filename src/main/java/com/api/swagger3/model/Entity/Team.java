package com.api.swagger3.model.Entity;

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

	@Column(name = "createDate")
	private String createDate;
}
