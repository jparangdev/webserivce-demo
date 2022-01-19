package kr.co.jparangdev.webservicedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing // auditing 이 뭔지 다시 공부해보기
@SpringBootApplication
public class WebserviceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceDemoApplication.class, args);
	}

}
