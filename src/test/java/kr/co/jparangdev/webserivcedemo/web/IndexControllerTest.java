package kr.co.jparangdev.webserivcedemo.web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void 메인페이지_로딩() {
		//when
		String body = testRestTemplate.getForObject("/",String.class);

		//then
		assertThat(body).isEqualTo("스프링 부트로 시작하는 웹 서비스");
	}

}