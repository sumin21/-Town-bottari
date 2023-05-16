package com.backend.townbottari.service;

import com.backend.townbottari.domain.form.CancelPosition;
import com.backend.townbottari.domain.form.Form;
import com.backend.townbottari.domain.form.dto.*;
import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.exception.BusinessException;
import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.repository.form.FormRepository;
import com.backend.townbottari.repository.post.PostRepository;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Transactional
@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FormRepository formRepository;


    public Page<FormListResponseDto> getDeliveriesListForDeliverer(Long userId, Pageable page) {
        // 배달 진행 중인 신청서
        // 게시글 작성자
        Page<Form> formPage = formRepository.findByPost_User_IdAndIsAcceptTrueAndIsCancelFalseAndIsEndFalse(userId, page);

        return formPage.map(FormListResponseDto::from);
    }

    public Page<FormListResponseDto> getDeliveriesListForConsumer(Long userId, Pageable page) {
        // 배달 진행 중인 신청서
        // 신청서 작성자
        Page<Form> formPage = formRepository.findByUserIdAndIsAcceptTrueAndIsCancelFalseAndIsEndFalse(userId, page);

        return formPage.map(FormListResponseDto::from);
    }

    public void cancelDeliveries(Long userId, Long formId, CancelDeliveryRequestDto requestDto) {
        Form form = formRepository.findById(formId).orElseThrow(NotFoundException::new);
        // 수락 && 파기전 && 종료전 상태여야 파기 가능
        if (!form.getIsAccept() && form.getIsCancel() && form.getIsEnd()) throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);
        if (Objects.equals(userId, form.getUser().getId())) {
            // 신청자인 경우
            form.setIsCancel(true);
            form.setCancelReason(requestDto.getCancelReason());
            form.setCancelPosition(CancelPosition.CONSUMER);
            formRepository.save(form);
        } else if (Objects.equals(userId, form.getPost().getUser().getId())) {
            // 대행자인 경우
            form.setIsCancel(true);
            form.setCancelReason(requestDto.getCancelReason());
            form.setCancelPosition(CancelPosition.DELIVERER);
            formRepository.save(form);
        } else {
            // 아무도 아닌 경우
            throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);
        }
    }
}