package com.backend.townbottari.service;

import com.backend.townbottari.domain.form.Form;
import com.backend.townbottari.domain.form.dto.FormRequestDto;
import com.backend.townbottari.domain.form.dto.FormResponseDto;
import com.backend.townbottari.domain.form.dto.FormUpdateRequestDto;
import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.post.dto.PostRequestDto;
import com.backend.townbottari.domain.post.dto.PostResponseDto;
import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.exception.BusinessException;
import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.repository.form.FormRepository;
import com.backend.townbottari.repository.post.PostRepository;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Transactional
@RequiredArgsConstructor
@Service
public class FormService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FormRepository formRepository;


    public FormResponseDto saveForm(Long userId, FormRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(NotFoundException::new);

        if (userId.equals(post.getUser().getId())) throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);

        Form newForm = Form.createForm(requestDto.getContent(), requestDto.getStoreAddr(), requestDto.getCharge(), user, post);
        formRepository.save(newForm);
        return FormResponseDto.from(newForm, userId);
    }

    public FormResponseDto getForm(Long userId, Long formId) {
        Form form = formRepository.findById(formId).orElseThrow(NotFoundException::new);
        if ((!Objects.equals(userId, form.getUser().getId())) && (!Objects.equals(userId, form.getPost().getUser().getId()))) throw new BusinessException(ExceptionCode.NOT_FOUND);

        return FormResponseDto.from(form, userId);
    }

    public FormResponseDto updateForm(Long userId, Long formId, FormUpdateRequestDto requestDto) {
        Form form = formRepository.findById(formId).orElseThrow(NotFoundException::new);
        // 신청자여야 수정 가능
        // 수락 전까지만 수정 가능
        if (!Objects.equals(userId, form.getUser().getId()) || form.getIsAccept()) throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);

        form.setContent(requestDto.getContent());
        form.setStoreAddr(requestDto.getStoreAddr());
        form.setCharge(requestDto.getCharge());
        formRepository.save(form);
        return FormResponseDto.from(form, userId);
    }

    public void deleteForm(Long userId, Long formId) {
        Form form = formRepository.findById(formId).orElseThrow(NotFoundException::new);
        // 신청자여야 삭제 가능
        // 수락 전까지만 삭제 가능
        if (!Objects.equals(userId, form.getUser().getId()) || form.getIsAccept()) throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);

        formRepository.delete(form);
    }

    public void acceptForm(Long userId, Long formId) {
        Form form = formRepository.findById(formId).orElseThrow(NotFoundException::new);
        // 게시글 작성자만 수락 가능
        // 수락 전까지만 수락 가능
        if (!Objects.equals(userId, form.getPost().getUser().getId()) || form.getIsAccept()) throw new BusinessException(ExceptionCode.INVALID_INPUT_VALUE);

        form.setIsAccept(true);
        formRepository.save(form);
    }
}