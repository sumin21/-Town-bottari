package com.backend.townbottari.domain.form.dto;

import com.backend.townbottari.domain.form.CancelReason;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelDeliveryRequestDto {

    @ApiModelProperty(value = "파기 사유")
    @NotBlank
    private CancelReason cancelReason;

}