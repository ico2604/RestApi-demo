package com.api.swagger3.model.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Education extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long eduKey;

    private int eduType;

	private String eduName;

	@CreatedDate
	private LocalDateTime regDate;

	private LocalDateTime modDate;
}
