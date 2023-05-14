package com.backend.townbottari.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUserNickNameDto {
    @ApiModelProperty(value = "수정할 닉네임")
    @NotBlank
    private String nickname;
}