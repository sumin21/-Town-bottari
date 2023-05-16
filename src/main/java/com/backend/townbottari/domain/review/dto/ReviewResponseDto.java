package com.backend.townbottari.domain.review.dto;

import com.backend.townbottari.domain.review.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponseDto {
    @ApiModelProperty(value = "리뷰 id")
    @NotBlank
    private Long reviewId;

    @ApiModelProperty(value = "리뷰 작성자 id")
    @NotBlank
    private Long writerId;

    @ApiModelProperty(value = "리뷰 대상자 id")
    @NotBlank
    private Long targetUserId;

    @ApiModelProperty(value = "본문")
    private String content;

    @ApiModelProperty(value = "별점 (1~5)")
    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    @ApiModelProperty(value = "댓글 작성자여부")
    @NotBlank
    private Boolean isWriter;

    @ApiModelProperty(value = "작성 시간")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @Builder
    public ReviewResponseDto(Long reviewId, Long writerId, Long targetUserId, String content, Integer score, Boolean isWriter, LocalDateTime createdDate) {
        this.reviewId = reviewId;
        this.writerId = writerId;
        this.targetUserId = targetUserId;
        this.content = content;
        this.score = score;
        this.isWriter = isWriter;
        this.createdDate = createdDate;
    }

    public static ReviewResponseDto from(Review review, Long userId) {
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .writerId(review.getReviewer().getId())
                .targetUserId(review.getTargetUser().getId())
                .content(review.getContent())
                .score(review.getScore())
                .isWriter(Objects.equals(review.getReviewer().getId(), userId))
                .createdDate(review.getCreatedDate())
                .build();
    }

}
