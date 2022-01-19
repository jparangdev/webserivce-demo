package kr.co.jparangdev.webservicedemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.jparangdev.webservicedemo.domain.Posts;
import kr.co.jparangdev.webservicedemo.domain.PostsRepository;
import kr.co.jparangdev.webservicedemo.web.dto.PostsListResponseDto;
import kr.co.jparangdev.webservicedemo.web.dto.PostsRequestSaveDto;
import kr.co.jparangdev.webservicedemo.web.dto.PostsRequestUpdateDto;
import kr.co.jparangdev.webservicedemo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostsService {

	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsRequestSaveDto request) {
		return postsRepository.save(request.toEntity()).getId();
	}

	public PostsResponseDto findById(Long id) {
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. Id =>"+id));
		return new PostsResponseDto(posts);
	}

	@Transactional
	public Long update(Long id, PostsRequestUpdateDto request) {
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. Id =>"+id));
		posts.update(request.getTitle(), request.getContent());
		return id;
	}

	@Transactional(readOnly = true)
	public List<PostsListResponseDto> findAllDesc() {
		return postsRepository.findAllDesc().stream()
			.map(PostsListResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public void delete(Long id) {
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=>"+id));
		postsRepository.delete(posts);
	}
}
