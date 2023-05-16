package com.backend.townbottari.controller;

import com.backend.townbottari.domain.form.dto.FormRequestDto;
import com.backend.townbottari.domain.form.dto.FormResponseDto;
import com.backend.townbottari.domain.form.dto.FormUpdateRequestDto;
import com.backend.townbottari.service.FormService;
import com.backend.townbottari.service.result.ResponseService;
import com.backend.townbottari.service.result.Result;
import com.backend.townbottari.service.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("api/forms")
@RestController
public class FormController {
    private final FormService formService;
    private final ResponseService responseService;

    @PostMapping()
    @ApiOperation(value = "신청서 작성 API", response = FormResponseDto.class)
    public ResponseEntity<SingleResult<FormResponseDto>> saveForm(@AuthenticationPrincipal Long userId, @RequestBody @Valid FormRequestDto request) {
        FormResponseDto result = formService.saveForm(userId, request);
        return ResponseEntity.ok(responseService.getSingleResult(result));

    }

    @GetMapping("/{formId}")
    @ApiOperation(value = "신청서 정보 반환 API", response = FormResponseDto.class)
    public ResponseEntity<SingleResult<FormResponseDto>> getForm(@AuthenticationPrincipal Long userId, @PathVariable Long formId) {
        FormResponseDto result = formService.getForm(userId, formId);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @PutMapping("/{formId}")
    @ApiOperation(value = "신청서 수정 API", response = FormResponseDto.class)
    public ResponseEntity<SingleResult<FormResponseDto>> updateForm(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long formId,
            @RequestBody @Valid FormUpdateRequestDto request) {

        FormResponseDto result = formService.updateForm(userId, formId, request);
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @DeleteMapping("/{formId}")
    @ApiOperation(value = "신청서 삭제 API", response = Result.class)
    public ResponseEntity<Result> deleteForm(
            @AuthenticationPrincipal Long userId, @PathVariable Long formId) {
        formService.deleteForm(userId, formId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/{formId}/acceptance")
    @ApiOperation(value = "신청서 수락 API", response = Result.class)
    public ResponseEntity<Result> acceptForm(
            @AuthenticationPrincipal Long userId, @PathVariable Long formId) {
        formService.acceptForm(userId, formId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }
}
