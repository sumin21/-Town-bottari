package com.backend.townbottari.domain.post.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequestDto {
    @ApiModelProperty(value = "제목")
    @NotBlank
    private String title;

    @ApiModelProperty(value = "본문")
    @NotBlank
    private String content;

    @ApiModelProperty(value = "도착지")
    @NotBlank
    private String arriveAddr;

    @ApiModelProperty(value = "경유지")
    @NotBlank
    private String wayAddr;

    @ApiModelProperty(value = "도착 예정시간")
    @NotBlank
    private LocalDateTime arriveTime;

    @ApiModelProperty(value = "희망 인원")
    @NotBlank
    private int hopeNum;

    @ApiModelProperty(value = "거래완료 인원")
    @NotBlank
    private int endNum;

    @ApiModelProperty(value = "수고비")
    @NotBlank
    private int charge;

    @ApiModelProperty(value = "카카오톡 오픈채팅 url")
    @NotBlank
    private String contactUrl;
}