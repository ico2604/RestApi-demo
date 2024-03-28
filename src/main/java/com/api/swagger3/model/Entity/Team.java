package com.api.swagger3.model.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@DynamicUpdate // 변경한 필드만 대응 Dirty Checking
public class Team extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamKey;

	private String teamName;

	@CreatedDate
	private LocalDateTime regDate;

	private LocalDateTime modDate;

	/**
	 *  CascadeType.ALL: 모든 Cascade를 적용
		CascadeType.PERSIST: 엔티티를 영속화할 때, 연관된 엔티티도 함께 유지
		CascadeType.MERGE: 엔티티 상태를 병합(Merge)할 때, 연관된 엔티티도 모두 병합
		CascadeType.REMOVE: 엔티티를 제거할 때, 연관된 엔티티도 모두 제거
		CascadeType.DETACH: 부모 엔티티를 detach() 수행하면, 연관 엔티티도 detach()상태가 되어 변경 사항 반영 X
		CascadeType.REFRESH: 상위 엔티티를 새로고침(Refresh)할 때, 연관된 엔티티도 모두 새로고침
	 */
}
