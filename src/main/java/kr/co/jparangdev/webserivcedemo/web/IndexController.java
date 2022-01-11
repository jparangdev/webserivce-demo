package kr.co.jparangdev.webserivcedemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.jparangdev.webserivcedemo.service.PostsService;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}

	@GetMapping("/posts/update/{id}")
	public String postUpdate(@PathVariable("id") Long id, Model model) {
		PostsResponseDto post = postsService.findById(id);
		model.addAttribute(post);

		return "posts-update";
	}
}
