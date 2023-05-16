package com.backend.townbottari.service;

import com.backend.townbottari.domain.form.Form;
import com.backend.townbottari.domain.form.dto.FormListResponseDto;
import com.backend.townbottari.domain.form.dto.FormResponseDto;
import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.post.dto.PostResponseDto;
import com.backend.townbottari.domain.post.dto.PostUpdateRequestDto;
import com.backend.townbottari.domain.review.Review;
import com.backend.townbottari.domain.review.dto.ReviewRequestDto;
import com.backend.townbottari.domain.review.dto.ReviewResponseDto;
import com.backend.townbottari.domain.review.dto.UpdateReviewRequestDto;
import com.backend.townbottari.domain.user.Role;
import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.exception.BusinessException;
import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.repository.form.FormRepository;
import com.backend.townbottari.repository.post.PostRepository;
import com.backend.townbottari.repository.review.ReviewRepository;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewResponseDto saveReview(Long userId, ReviewRequestDto requestDto) {
        if (Objects.equals(userId, requestDto.getTargetUserId()))
            throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);

        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        User targetUser = userRepository.findById(requestDto.getTargetUserId()).orElseThrow(NotFoundException::new);

        // 해당 유저가 타겟유저의 리뷰를 이미 작성한 경우 X
        Optional<Review> existReview = reviewRepository.findByTargetUserIdAndReviewerId(requestDto.getTargetUserId(), userId);
        if(existReview.isPresent()) throw new BusinessException(ExceptionCode.DUPLICATED_USER);

        Review newReview = Review.createReview(requestDto.getContent(), requestDto.getScore(), targetUser, user);
        reviewRepository.save(newReview);

        return ReviewResponseDto.from(newReview, userId);
    }

    public ReviewResponseDto getReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new);

        return ReviewResponseDto.from(review, userId);
    }

    public ReviewResponseDto updateReview(Long userId, Long reviewId, UpdateReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new);
        if (!Objects.equals(userId, review.getReviewer().getId())) {
            throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);
        }

        review.setContent(requestDto.getContent());
        review.setScore(review.getScore());
        reviewRepository.save(review);
        return ReviewResponseDto.from(review, userId);
    }

    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new);
        if (!Objects.equals(userId, review.getReviewer().getId())) {
            throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);
        }

        reviewRepository.delete(review);
    }

    public Page<ReviewResponseDto> getTargetUsersReviews(Long userId, Long targetUserId, Pageable page) {
        User targetUser = userRepository.findById(targetUserId).orElseThrow(NotFoundException::new);

        Page<Review> reviewPage = reviewRepository.findByTargetUserId(targetUserId, page);
        List<ReviewResponseDto> dtoList = reviewPage.getContent()
                .stream()
                .map(review -> ReviewResponseDto.from(review, userId))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, page, reviewPage.getTotalElements());
    }

}