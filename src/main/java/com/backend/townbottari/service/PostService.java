package com.backend.townbottari.service;

import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.post.dto.PostListResponseDto;
import com.backend.townbottari.domain.post.dto.PostRequestDto;
import com.backend.townbottari.domain.post.dto.PostResponseDto;
import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.exception.BusinessException;
import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.repository.post.PostRepository;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public PostResponseDto savePost(Long userId, PostRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Post newPost = Post.createPost(requestDto.getTitle(), requestDto.getContent(), requestDto.getArriveAddr(), requestDto.getWayAddr(), requestDto.getArriveTime(), requestDto.getHopeNum(), requestDto.getEndNum(), requestDto.getCharge(), requestDto.getContactUrl(), user);
        postRepository.save(newPost);
        return PostResponseDto.from(newPost, userId);
    }

    public PostResponseDto getPost(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NotFoundException::new);
        return PostResponseDto.from(post, userId);
    }

    public PostResponseDto updatePost(Long userId, Long postId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(NotFoundException::new);
        if (!Objects.equals(userId, post.getUser().getId())) {
            throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setArriveAddr(requestDto.getArriveAddr());
        post.setWayAddr(requestDto.getWayAddr());
        post.setArriveTime(requestDto.getArriveTime());
        post.setHopeNum(requestDto.getHopeNum());
        post.setEndNum(requestDto.getEndNum());
        post.setCharge(requestDto.getCharge());
        post.setContactUrl(requestDto.getContactUrl());
        postRepository.save(post);
        return PostResponseDto.from(post, userId);
    }

    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NotFoundException::new);
        if (!Objects.equals(userId, post.getUser().getId())) {
            throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);
        }

        postRepository.delete(post);
    }

    public Page<PostListResponseDto> getPostList(Long userId, String title, String addr, String sortType, Pageable page) {
        Page<Post> postPage = null;

        if (sortType.equals("최신순")) {
            if (title == null && addr == null) {
                postPage = postRepository.findByOrderByCreatedDateAsc(page);
            } else if (title == null) {
                postPage = postRepository.findByArriveAddrOrderByCreatedDateAsc(addr, page);
            } else if (addr == null) {
                postPage = postRepository.findByTitleContainingOrderByCreatedDateAsc(title, page);
            } else {
                postPage = postRepository.findByTitleContainingAndArriveAddrOrderByCreatedDateAsc(title, addr, page);
            }
        } else if (sortType.equals("대행비순")) {
            if (title == null && addr == null) {
                postPage = postRepository.findByOrderByChargeAsc(page);
            } else if (title == null) {
                postPage = postRepository.findByArriveAddrOrderByChargeAsc(addr, page);
            } else if (addr == null) {
                postPage = postRepository.findByTitleContainingOrderByChargeAsc(title, page);
            } else {
                postPage = postRepository.findByTitleContainingAndArriveAddrOrderByChargeAsc(title, addr, page);
            }
        } else {
            throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);
        }

        return postPage.map(PostListResponseDto::from);
    }
}
