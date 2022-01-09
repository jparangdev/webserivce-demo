package kr.co.jparangdev.webserivcedemo.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloDto {

	private final String name;
	private final int amount;
}
