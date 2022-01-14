package kr.co.jparangdev.webserivcedemo.web;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.jparangdev.webserivcedemo.domain.Posts;
import kr.co.jparangdev.webserivcedemo.domain.PostsRepository;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsRequestSaveDto;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsRequestUpdateDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 이게무슨설정이지
class PostsControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();
	}

	@AfterEach
	public void tearDown() throws Exception {
		postsRepository.deleteAll();
	}

	@Test
	@WithMockUser(roles="USER")
	public void Posts_등록된다() throws Exception {
		//given
		String title = "title";
		String content = "content";
		PostsRequestSaveDto requestDto = PostsRequestSaveDto.builder()
			.title(title)
			.content(content)
			.author("author")
			.build();

		String url = "http://localhost:" + port + "/api/v1/posts";

		//when
		mvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(new ObjectMapper().writeValueAsString(requestDto)))
			.andExpect(status().isOk());

		//then
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
	}

	@Test
	@WithMockUser(roles="USER")
	public void Posts_수정된다() throws Exception {
		//given
		Posts savedPosts = postsRepository.save(Posts.builder()
			.title("title")
			.content("content")
			.author("author")
			.build());

		Long updateId = savedPosts.getId();
		String expectedTitle = "title2";
		String expectedContent = "content2";

		PostsRequestUpdateDto requestDto = PostsRequestUpdateDto.builder()
			.title(expectedTitle)
			.content(expectedContent)
			.build();

		String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

		//when
		mvc.perform(put(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(new ObjectMapper().writeValueAsString(requestDto)))
			.andExpect(status().isOk());

		//then
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
	}



}