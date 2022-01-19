package kr.co.jparangdev.webservicedemo.web.dto;

import java.time.LocalDateTime;

import kr.co.jparangdev.webservicedemo.domain.Posts;
import lombok.Getter;

@Getter
public class PostsListResponseDto {

	private Long id;
	private String title;
	private String content;
	private String author;
	private LocalDateTime modifiedDate;

	public PostsListResponseDto(Posts post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.author = post.getAuthor();
		this.modifiedDate = post.getModifiedDate();
	}
}
