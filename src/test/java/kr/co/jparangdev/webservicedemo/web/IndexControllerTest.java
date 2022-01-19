package kr.co.jparangdev.webservicedemo.web;

import static org.assertj.core.api.Assertions.*;

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
		assertThat(body).contains("스프링부트 웹서비스");
	}

}