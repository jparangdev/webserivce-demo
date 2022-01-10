package kr.co.jparangdev.webserivcedemo.web;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.jparangdev.webserivcedemo.domain.Posts;
import kr.co.jparangdev.webserivcedemo.domain.PostsRepository;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsRequestSaveDto;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsRequestUpdateDto;

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

	@Test
	public void Posts_수정테스트() {
		//given
		String title = "title";
		String content = "content";
		PostsRequestSaveDto requestSaveDto = PostsRequestSaveDto.builder()
			.title(title)
			.content(content)
			.author("author")
			.build();

		String url = "http://localhost:"+port+"/api/v1/posts";
		ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestSaveDto, Long.class);

		Long id = responseEntity.getBody();
		String title2 = "title2";
		String content2 = "content2";

		HttpEntity<PostsRequestUpdateDto> requestEntity = new HttpEntity<>(new PostsRequestUpdateDto(title2, content2));

		//when
		ResponseEntity<Long> responseEntity2 = testRestTemplate.exchange(url+"/"+id, HttpMethod.PUT, requestEntity, Long.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		Optional<Posts> byId = repository.findById(responseEntity.getBody());
		assertThat(byId.get().getTitle()).isEqualTo(title2);
		assertThat(byId.get().getContent()).isEqualTo(content2);
	}

	@Test
	public void Posts_BaseEntityTest() {
		//given
		LocalDateTime now = LocalDateTime.now();
		String title = "title";
		String content = "content";
		PostsRequestSaveDto requestSaveDto = PostsRequestSaveDto.builder()
			.title(title)
			.content(content)
			.author("author")
			.build();

		String url = "http://localhost:"+port+"/api/v1/posts";
		ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestSaveDto, Long.class);

		Posts createdPosts = repository.findById(responseEntity.getBody()).orElseThrow(()->new IllegalArgumentException("게시글이없습니다."));
		assertThat(createdPosts.getCreatedDate()).isAfter(now);


		Long id = responseEntity.getBody();
		String title2 = "title2";
		String content2 = "content2";

		HttpEntity<PostsRequestUpdateDto> requestEntity = new HttpEntity<>(new PostsRequestUpdateDto(title2, content2));

		//when
		ResponseEntity<Long> responseEntity2 = testRestTemplate.exchange(url+"/"+id, HttpMethod.PUT, requestEntity, Long.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		Optional<Posts> byId = repository.findById(responseEntity.getBody());
		assertThat(byId.get().getTitle()).isEqualTo(title2);
		assertThat(byId.get().getContent()).isEqualTo(content2);
		assertThat(byId.get().getModifiedDate()).isAfter(now);
	}



}