package com.backend.townbottari.service.result;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class MultiplePageResult<T> extends Result {
    private Page<T> pageData;
}
