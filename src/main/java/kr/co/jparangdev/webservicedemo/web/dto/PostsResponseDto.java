package kr.co.jparangdev.webservicedemo.web.dto;

import kr.co.jparangdev.webservicedemo.domain.Posts;

public class PostsResponseDto {
	private Long id;

	private String title;

	private String content;

	private String author;

	public PostsResponseDto(Long id, String title, String content, String author) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public PostsResponseDto(Posts posts) {
		this.id = posts.getId();
		this.title = posts.getTitle();
		this.content = posts.getContent();
		this.author = posts.getAuthor();
	}

}
