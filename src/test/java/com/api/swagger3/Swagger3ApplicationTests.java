package com.api.swagger3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.swagger3.service.MemberService;
/*
 * 단위 테스트 사용방법
 * https://trace90.tistory.com/entry/SpringBoot-JUnit5-%EB%8B%A8%EC%9C%84-%ED%85%8C%EC%8A%A4%ED%8A%B8
 */
@SpringBootTest
class Swagger3ApplicationTests {

	@Autowired
	private MemberService memberService;
	/*
	 * [ given/when/then 패턴 ]
	 * 요즘 단위테스트는 거의 given-when-then 패턴으로 작성하는 추세이다. 
	 * given-when-then 패턴이란 1개의 단위 테스트를 3가지 단계로 나누어 처리하는 패턴으로, 
	 * 각각의 단계는 다음을 의미한다.
	 * 
	 * given(준비): 어떠한 데이터가 준비되었을 때
	 * when(실행): 어떠한 함수를 실행하면
	 * then(검증): 어떠한 결과가 나와야 한다.
	 */
	@Test
	@DisplayName("test")
	void contextLoads() {
		int val1 = 3 * 1;
		int val2 = 2 * 5;
		assertEquals(val1, val2);
	}

}
