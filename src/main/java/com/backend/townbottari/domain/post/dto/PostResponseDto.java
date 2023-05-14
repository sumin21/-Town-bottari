package com.backend.townbottari.domain.post.dto;

import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
    private boolean isEnd;

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

    @Builder
    public PostResponseDto(Long postId, Long userId, String title, String content, boolean isEnd, String arriveAddr,
                           String wayAddr, LocalDateTime arriveTime, int hopeNum, int endNum, int charge, String contactUrl) {
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
    }

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .isEnd(post.isEnd())
                .arriveAddr(post.getArriveAddr())
                .wayAddr(post.getWayAddr())
                .arriveTime(post.getArriveTime())
                .hopeNum(post.getHopeNum())
                .endNum(post.getEndNum())
                .charge(post.getCharge())
                .contactUrl(post.getContactUrl())
                .build();
    }
}
