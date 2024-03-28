package com.api.swagger3.model.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@DynamicUpdate // 변경한 필드만 대응 Dirty Checking
/**
 * @Date에는 @toString이 포함되어 있어 엔티티에서 사용하지 않는것이 좋다.
 * @toString 사용시 불필요한 즉시 로딩이 발생할 수 있다.
 */
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

	//EAGER(즉시로딩), LAZY(지연로딩)으로 
	//즉시로딩은 조회시 자동으로 조인 칼럼과 조인을 하며, 지연로딩은 자동으로 조인시키지 않는다. 
	//하지만 DTO @QueryProjection으로 SELECT받을때 조인 변수의 칼럼을 SELECT에 추가하면 자동 조인 한다.
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "teamKey")
	private Team team;
}
