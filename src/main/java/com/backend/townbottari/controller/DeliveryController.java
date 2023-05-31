package com.backend.townbottari.controller;

import com.backend.townbottari.domain.form.dto.*;
import com.backend.townbottari.service.DeliveryService;
import com.backend.townbottari.service.FormService;
import com.backend.townbottari.service.result.MultiplePageResult;
import com.backend.townbottari.service.result.ResponseService;
import com.backend.townbottari.service.result.Result;
import com.backend.townbottari.service.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("api/deliveries")
@RestController
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final ResponseService responseService;

    @GetMapping("/deliverer")
    @ApiOperation(value = "대행자의 거래 목록 반환 API", response = FormListResponseDto.class)
    public ResponseEntity<MultiplePageResult<FormListResponseDto>> getDeliveriesListForDeliverer(@AuthenticationPrincipal Long userId, @PageableDefault(size=10) Pageable pageable) {
        Page<FormListResponseDto> result = deliveryService.getDeliveriesListForDeliverer(userId, pageable);
        return ResponseEntity.ok(responseService.getMultiplePageResult(result));
    }

    @GetMapping("/consumer")
    @ApiOperation(value = "소비자의 거래 목록 반환 API", response = FormListResponseDto.class)
    public ResponseEntity<MultiplePageResult<FormListResponseDto>> getDeliveriesListForConsumer(@AuthenticationPrincipal Long userId, @PageableDefault(size=10) Pageable pageable) {
        Page<FormListResponseDto> result = deliveryService.getDeliveriesListForConsumer(userId, pageable);
        return ResponseEntity.ok(responseService.getMultiplePageResult(result));
    }

    @PostMapping("/{formId}/cancel")
    @ApiOperation(value = "거래 파기 신청 API", response = Result.class)
    public ResponseEntity<Result> cancelDeliveries(
            @AuthenticationPrincipal Long userId, @PathVariable Long formId, @RequestBody @Valid CancelDeliveryRequestDto requestDto) {
        deliveryService.cancelDeliveries(userId, formId, requestDto);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/{formId}/cancel-acceptance")
    @ApiOperation(value = "거래 파기 수락 API", response = Result.class)
    public ResponseEntity<Result> acceptCancelDeliveries(
            @AuthenticationPrincipal Long userId, @PathVariable Long formId, @RequestBody @Valid AcceptCancelDeliveryRequestDto requestDto) {
        deliveryService.acceptCancelDeliveries(userId, formId, requestDto);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/{formId}/success")
    @ApiOperation(value = "거래 완료 API", response = Result.class)
    public ResponseEntity<Result> successDeliveries(
            @AuthenticationPrincipal Long userId, @PathVariable Long formId) {
        deliveryService.successDeliveries(userId, formId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }
}
