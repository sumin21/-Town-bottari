package com.backend.townbottari.service.result;

import lombok.Data;

@Data
public class SingleResult<T> extends Result {
    private T data;
}
