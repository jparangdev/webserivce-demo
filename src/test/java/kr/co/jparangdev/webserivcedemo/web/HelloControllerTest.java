package kr.co.jparangdev.webserivcedemo.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import kr.co.jparangdev.webservicedemo.config.auth.SecurityConfig;
import kr.co.jparangdev.webservicedemo.web.HelloController;

@WebMvcTest(controllers = HelloController.class,
excludeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
class HelloControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser(roles = "USER")
	public void Hello_테스트() throws Exception {
		String hello = "hello jparangdev !";

		mockMvc.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string(hello));
	}

	@Test
	@WithMockUser(roles = "USER")
	public void Hello_Dto_테스트() throws Exception {
		String name = "jparangdev";
		int amount= 100;

		mockMvc.perform(get("/hello/dto")
				.param("name",name)
				.param("amount", String.valueOf(amount))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name",is(name)))
			.andExpect(jsonPath("$.amount",is(amount)));
	}

}