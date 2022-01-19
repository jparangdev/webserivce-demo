package kr.co.jparangdev.webservicedemo.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.jparangdev.webservicedemo.config.auth.LoginUser;
import kr.co.jparangdev.webservicedemo.config.auth.dto.SessionUser;
import kr.co.jparangdev.webservicedemo.service.PostsService;
import kr.co.jparangdev.webservicedemo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final PostsService postsService;
	private final HttpSession session;

	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user) {

		model.addAttribute("posts", postsService.findAllDesc());

		if(user != null) {
			model.addAttribute("userName", user.getName());
		}

		return "index";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}

	@GetMapping("/posts/update/{id}")
	public String postUpdate(@PathVariable("id") Long id, Model model) {
		PostsResponseDto post = postsService.findById(id);
		model.addAttribute("post", post);

		return "posts-update";
	}
}
