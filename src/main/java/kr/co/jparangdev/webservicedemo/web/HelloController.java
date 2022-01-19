package kr.co.jparangdev.webservicedemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.jparangdev.webservicedemo.web.dto.HelloDto;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "hello jparangdev !";
	}

	@GetMapping("/hello/dto")
	public HelloDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
		return new HelloDto(name, amount);
	}
}
