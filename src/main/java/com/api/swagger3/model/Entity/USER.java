package com.api.swagger3.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class USER {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Long id;

	@Column(name = "userId")
    private String userId;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "email")
	private String email;

	@Column(name = "sex")
	private String sex;

	@Column(name = "birthDate")
	private String birthDate;

	@Column(name = "phoneNumber")
	private String phoneNumber;
}
