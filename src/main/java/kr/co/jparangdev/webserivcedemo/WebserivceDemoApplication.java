package kr.co.jparangdev.webserivcedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // auditing 이 뭔지 다시 공부해보기
@SpringBootApplication
public class WebserivceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserivceDemoApplication.class, args);
	}

}
