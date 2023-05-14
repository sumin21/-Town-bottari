package com.backend.townbottari.domain.post.dto;

import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDto {
    @ApiModelProperty(value = "게시글 id")
    @NotBlank
    private Long postId;

    @ApiModelProperty(value = "작성자 id")
    @NotBlank
    private Long userId;

    @ApiModelProperty(value = "제목")
    @NotBlank
    private String title;

    @ApiModelProperty(value = "본문")
    @NotBlank
    private String content;

    @ApiModelProperty(value = "거래여부")
    @NotBlank
    private Boolean isEnd;

    @ApiModelProperty(value = "도착지")
    @NotBlank
    private String arriveAddr;

    @ApiModelProperty(value = "경유지")
    @NotBlank
    private String wayAddr;

    @ApiModelProperty(value = "도착 예정시간")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime arriveTime;

    @ApiModelProperty(value = "희망 인원")
    @NotNull
    @Min(0)
    private Integer hopeNum;

    @ApiModelProperty(value = "거래완료 인원")
    @NotNull
    @Min(0)
    private Integer endNum;

    @ApiModelProperty(value = "수고비")
    @NotNull
    @Min(0)
    private Integer charge;

    @ApiModelProperty(value = "카카오톡 오픈채팅 url")
    @NotBlank
    private String contactUrl;

    @ApiModelProperty(value = "작성자여부")
    @NotBlank
    private Boolean isWriter;

    @Builder
    public PostResponseDto(Long postId, Long userId, String title, String content, Boolean isEnd, String arriveAddr,
                           String wayAddr, LocalDateTime arriveTime, Integer hopeNum, Integer endNum, Integer charge, String contactUrl, Boolean isWriter) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.isEnd = isEnd;
        this.arriveAddr = arriveAddr;
        this.wayAddr = wayAddr;
        this.arriveTime = arriveTime;
        this.hopeNum = hopeNum;
        this.endNum = endNum;
        this.charge = charge;
        this.contactUrl = contactUrl;
        this.isWriter = isWriter;
    }

    public static PostResponseDto from(Post post, Long userId) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .isEnd(post.getIsEnd())
                .arriveAddr(post.getArriveAddr())
                .wayAddr(post.getWayAddr())
                .arriveTime(post.getArriveTime())
                .hopeNum(post.getHopeNum())
                .endNum(post.getEndNum())
                .charge(post.getCharge())
                .contactUrl(post.getContactUrl())
                .isWriter(Objects.equals(post.getUser().getId(), userId))
                .build();
    }
}
