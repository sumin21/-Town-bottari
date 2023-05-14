package com.backend.townbottari.domain.form.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormRequestDto {
    @ApiModelProperty(value = "게시글 아이디")
    @NotNull
    private Long postId;

    @ApiModelProperty(value = "본문")
    @NotBlank
    private String content;

    @ApiModelProperty(value = "가게위치")
    @NotBlank
    private String storeAddr;

    @ApiModelProperty(value = "가격")
    @NotNull
    @Min(0)
    private Integer charge;
}