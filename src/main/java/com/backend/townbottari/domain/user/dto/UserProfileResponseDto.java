package com.backend.townbottari.domain.user.dto;

import com.backend.townbottari.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfileResponseDto {
    @ApiModelProperty(value = "사용자 id")
    @NotBlank
    private Long userId;

    @ApiModelProperty(value = "닉네임 (소셜 계정 이름)")
    @NotBlank
    private String nickname;

    @ApiModelProperty(value = "회원 유형 (카카오/관리자)")
    @NotBlank
    private String socialType;

    @ApiModelProperty(value = "가상 포인트")
    @NotBlank
    private Long money;

    @Builder
    public UserProfileResponseDto(Long userId, String nickname, String socialType, Long money) {
        this.userId = userId;
        this.nickname = nickname;
        this.socialType = socialType;
        this.money = money;
    }

    public static UserProfileResponseDto from(User user) {
        return UserProfileResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .socialType(user.getRole().getValue())
                .money(user.getMoney())
                .build();
    }
}
