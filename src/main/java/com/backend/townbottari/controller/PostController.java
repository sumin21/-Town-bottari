package com.backend.townbottari.controller;

import com.backend.townbottari.domain.post.dto.PostRequestDto;
import com.backend.townbottari.domain.post.dto.PostResponseDto;
import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.domain.user.dto.TokenRefreshRequestDto;
import com.backend.townbottari.domain.user.dto.UserProfileResponseDto;
import com.backend.townbottari.service.AuthService;
import com.backend.townbottari.service.PostService;
import com.backend.townbottari.service.result.ResponseService;
import com.backend.townbottari.service.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("api/posts")
@RestController
public class PostController {
    private final PostService postService;
    private final ResponseService responseService;

    @PostMapping()
    @ApiOperation(value = "게시글 개시 API", response = PostResponseDto.class)
    public ResponseEntity<SingleResult<PostResponseDto>> savePost(@AuthenticationPrincipal Long userId, @RequestBody @Valid PostRequestDto request) {
        PostResponseDto result = postService.savePost(userId, request);
        return ResponseEntity.ok(responseService.getSingleResult(result));

    }
}
