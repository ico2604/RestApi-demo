package com.api.swagger3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysema.commons.lang.Assert;

@SpringBootTest
class Swagger3ApplicationTests {

	@Test
	@DisplayName("test")
	void contextLoads() {
		Assert.assertThat(false, "propOrMsg", "msgSuffix", "rv");
	}

}
