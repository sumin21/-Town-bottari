package com.backend.townbottari.domain.form.dto;

import com.backend.townbottari.domain.form.CancelPosition;
import com.backend.townbottari.domain.form.CancelReason;
import com.backend.townbottari.domain.form.Form;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormListResponseDto {
    @ApiModelProperty(value = "신청서 id")
    @NotBlank
    private Long formId;

    @ApiModelProperty(value = "게시글 id")
    @NotBlank
    private Long postId;

    @ApiModelProperty(value = "작성자 id")
    @NotBlank
    private Long userId;

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

    @ApiModelProperty(value = "수락여부")
    @NotNull
    private Boolean isAccept;

    @ApiModelProperty(value = "파기여부")
    @NotNull
    private Boolean isCancel;

    @ApiModelProperty(value = "파기사유")
    @NotBlank
    private CancelReason cancelReason;

    @ApiModelProperty(value = "파기신청자")
    @NotBlank
    private CancelPosition cancelPosition;

    @ApiModelProperty(value = "거래종료여부")
    @NotNull
    private Boolean isEnd;

    @ApiModelProperty(value = "작성 시간")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @Builder
    public FormListResponseDto(Long formId, Long postId, Long userId, String content, String storeAddr, Integer charge,
                           Boolean isAccept, Boolean isCancel, CancelReason cancelReason, CancelPosition cancelPosition,
                           Boolean isEnd, LocalDateTime createdDate) {
        this.formId = formId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.storeAddr = storeAddr;
        this.charge = charge;
        this.isAccept = isAccept;
        this.isCancel = isCancel;
        this.cancelReason = cancelReason;
        this.cancelPosition = cancelPosition;
        this.isEnd = isEnd;
        this.createdDate = createdDate;
    }

    public static FormListResponseDto from(Form form) {
        return FormListResponseDto.builder()
                .formId(form.getId())
                .postId(form.getPost().getId())
                .userId(form.getUser().getId())
                .content(form.getContent())
                .storeAddr(form.getStoreAddr())
                .charge(form.getCharge())
                .isAccept(form.getIsAccept())
                .isCancel(form.getIsCancel())
                .cancelReason(form.getCancelReason())
                .cancelPosition(form.getCancelPosition())
                .isEnd(form.getIsEnd())
                .createdDate(form.getCreatedDate())
                .build();
    }

}
