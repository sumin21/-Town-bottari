package com.backend.townbottari.domain.review.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewRequestDto {

    @ApiModelProperty(value = "본문")
    private String content;

    @ApiModelProperty(value = "별점 (1~5)")
    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    @ApiModelProperty(value = "리뷰 대상자 ID")
    @NotNull
    @Min(0)
    private Long targetUserId;

}