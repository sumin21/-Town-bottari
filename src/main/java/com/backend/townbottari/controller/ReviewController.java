package com.backend.townbottari.controller;

import com.backend.townbottari.domain.form.dto.AcceptCancelDeliveryRequestDto;
import com.backend.townbottari.domain.form.dto.CancelDeliveryRequestDto;
import com.backend.townbottari.domain.form.dto.FormListResponseDto;
import com.backend.townbottari.domain.post.dto.PostResponseDto;
import com.backend.townbottari.domain.post.dto.PostUpdateRequestDto;
import com.backend.townbottari.domain.review.dto.ReviewRequestDto;
import com.backend.townbottari.domain.review.dto.ReviewResponseDto;
import com.backend.townbottari.domain.review.dto.UpdateReviewRequestDto;
import com.backend.townbottari.service.DeliveryService;
import com.backend.townbottari.service.ReviewService;
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

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("api/reviews")
@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final ResponseService responseService;

    @PostMapping()
    @ApiOperation(value = "리뷰 작성 API", response = ReviewResponseDto.class)
    public ResponseEntity<SingleResult<ReviewResponseDto>> saveReview(@AuthenticationPrincipal Long userId, @RequestBody @Valid ReviewRequestDto requestDto) {
        ReviewResponseDto result = reviewService.saveReview(userId, requestDto);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @GetMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 반환 API", response = ReviewResponseDto.class)
    public ResponseEntity<SingleResult<ReviewResponseDto>> getReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long reviewId) {

        ReviewResponseDto result = reviewService.getReview(userId, reviewId);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @PutMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 수정 API", response = ReviewResponseDto.class)
    public ResponseEntity<SingleResult<ReviewResponseDto>> updateReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long reviewId,
            @RequestBody @Valid UpdateReviewRequestDto request) {

        ReviewResponseDto result = reviewService.updateReview(userId, reviewId, request);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @DeleteMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 삭제 API", response = Result.class)
    public ResponseEntity<Result> deleteReview(
            @AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @GetMapping("/target-user/{targetUserId}")
    @ApiOperation(value = "targetUserId에 대한 리뷰 목록 조회 API", response = ReviewResponseDto.class)
    public ResponseEntity<MultiplePageResult<ReviewResponseDto>> getTargetUsersReviews(@AuthenticationPrincipal Long userId, @PathVariable Long targetUserId, @PageableDefault(size=10) Pageable pageable) {
        Page<ReviewResponseDto> result = reviewService.getTargetUsersReviews(userId, targetUserId, pageable);
        return ResponseEntity.ok(responseService.getMultiplePageResult(result));
    }

}
