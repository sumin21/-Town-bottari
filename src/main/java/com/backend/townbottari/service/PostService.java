package com.backend.townbottari.service;

import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.post.dto.PostRequestDto;
import com.backend.townbottari.domain.post.dto.PostResponseDto;
import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.repository.post.PostRepository;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return PostResponseDto.from(newPost);
    }



}
