package kr.co.jparangdev.webserivcedemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.jparangdev.webserivcedemo.domain.Posts;
import kr.co.jparangdev.webserivcedemo.domain.PostsRepository;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsRequestSaveDto;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsRequestUpdateDto;
import kr.co.jparangdev.webserivcedemo.web.dto.PostsResponseDto;
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
		return PostsResponseDto.fromEntity(posts);
	}

	@Transactional
	public Long update(Long id, PostsRequestUpdateDto request) {
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. Id =>"+id));
		posts.update(request.getTitle(), request.getContent());
		return id;
	}
}
