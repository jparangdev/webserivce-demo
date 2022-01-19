package kr.co.jparangdev.webservicedemo.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsRequestUpdateDto {
	private String title;
	private String content;

	@Builder
	public PostsRequestUpdateDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
