package com.api.swagger3.model.Entity;

import org.springframework.data.jpa.repository.Temporal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class member {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberKey")
    private Long memberKey;

	@Column(name = "memberId")
    private String memberId;

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
