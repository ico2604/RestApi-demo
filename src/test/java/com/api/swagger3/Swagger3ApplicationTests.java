package com.api.swagger3;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@Test
	@DisplayName("test")
	void contextLoads() {
		int val1 = 3 * 1;
		int val2 = 2 * 5;
		assertEquals(val1, val2);
	}

}
