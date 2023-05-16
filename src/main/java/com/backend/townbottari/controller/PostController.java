package com.backend.townbottari.controller;

import com.backend.townbottari.domain.form.dto.FormListResponseDto;
import com.backend.townbottari.domain.form.dto.FormResponseDto;
import com.backend.townbottari.domain.post.dto.PostListResponseDto;
import com.backend.townbottari.domain.post.dto.PostRequestDto;
import com.backend.townbottari.domain.post.dto.PostResponseDto;

import com.backend.townbottari.domain.post.dto.PostUpdateRequestDto;
import com.backend.townbottari.service.PostService;
import com.backend.townbottari.service.result.MultiplePageResult;
import com.backend.townbottari.service.result.ResponseService;
import com.backend.townbottari.service.result.Result;
import com.backend.townbottari.service.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("api/posts")
@RestController
public class PostController {
    private final PostService postService;
    private final ResponseService responseService;

    @GetMapping()
    @ApiOperation(value = "게시글 목록 반환 API", response = PostResponseDto.class)
    public ResponseEntity<MultiplePageResult<PostListResponseDto>> getPostList(
            @AuthenticationPrincipal Long userId,
            @RequestParam(name="title", required = false) String title,
            @RequestParam(name="addr", required = false) String addr,
            @RequestParam(name="type", required = true) String type,
            @PageableDefault(size=20) Pageable pageable) {

        Page<PostListResponseDto> response = postService.getPostList(userId, title, addr, type, pageable);

        return ResponseEntity.ok(responseService.getMultiplePageResult(response));
    }

    @PostMapping()
    @ApiOperation(value = "게시글 게시 API", response = PostResponseDto.class)
    public ResponseEntity<SingleResult<PostResponseDto>> savePost(@AuthenticationPrincipal Long userId, @RequestBody @Valid PostRequestDto request) {
        PostResponseDto result = postService.savePost(userId, request);
        return ResponseEntity.ok(responseService.getSingleResult(result));

    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "게시글 정보 반환 API", response = PostResponseDto.class)
    public ResponseEntity<SingleResult<PostResponseDto>> getPost(@AuthenticationPrincipal Long userId, @PathVariable Long postId) {
        PostResponseDto result = postService.getPost(userId, postId);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @PutMapping("/{postId}")
    @ApiOperation(value = "게시글 수정 API", response = PostResponseDto.class)
    public ResponseEntity<SingleResult<PostResponseDto>> updatePost(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateRequestDto request) {

        PostResponseDto result = postService.updatePost(userId, postId, request);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @DeleteMapping("/{postId}")
    @ApiOperation(value = "게시글 삭제 API", response = Result.class)
    public ResponseEntity<Result> deletePost(
            @AuthenticationPrincipal Long userId, @PathVariable Long postId) {
        postService.deletePost(userId, postId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @GetMapping("/{postId}/forms")
    @ApiOperation(value = "해당 게시글의 신청서 목록 조회 API", response = FormListResponseDto.class)
    public ResponseEntity<MultiplePageResult<FormListResponseDto>> getPostsForms(@AuthenticationPrincipal Long userId, @PathVariable Long postId, @PageableDefault(size=10) Pageable pageable) {
        Page<FormListResponseDto> result = postService.getPostsForms(userId, postId, pageable);
        return ResponseEntity.ok(responseService.getMultiplePageResult(result));
    }
}
