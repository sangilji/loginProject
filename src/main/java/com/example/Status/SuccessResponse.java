package com.example.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SuccessResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;

    public SuccessResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }


}
