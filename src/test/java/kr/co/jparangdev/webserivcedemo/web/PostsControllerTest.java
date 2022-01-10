package kr.co.jparangdev.webserivcedemo.web;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.jparangdev.webserivcedemo.domain.Posts;
import kr.co.jparangdev.webserivcedemo.domain.PostsRepository;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsRequestSaveDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 이게무슨설정이지
class PostsControllerTest {

	@Autowired
	private PostsRepository repository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	@AfterEach
	void cleanup() {
		repository.deleteAll();
	}

	@Test
	public void Posts_등록테스트() {
		//given
		String title = "title";
		String content = "content";
		PostsRequestSaveDto requestSaveDto = PostsRequestSaveDto.builder()
			.title(title)
			.content(content)
			.author("author")
			.build();

		String url = "http://localhost:"+port+"/api/v1/posts";

		//when
		ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestSaveDto, Long.class);


		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		Optional<Posts> byId = repository.findById(responseEntity.getBody());
		assertThat(byId.get().getTitle()).isEqualTo(title);
		assertThat(byId.get().getContent()).isEqualTo(content);
	}

}