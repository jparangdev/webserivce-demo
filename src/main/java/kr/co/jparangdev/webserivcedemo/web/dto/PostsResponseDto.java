package kr.co.jparangdev.webserivcedemo.web.dto;

import kr.co.jparangdev.webserivcedemo.domain.Posts;

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

	public static PostsResponseDto fromEntity(Posts posts) {
		return new PostsResponseDto(posts.getId(), posts.getTitle(), posts.getContent(), posts.getAuthor());
	}
}
