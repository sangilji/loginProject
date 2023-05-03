package com.example.login.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class DefaultResponse<T> {

    private final boolean success;
    private final String responseMessage;
    private final T data;

    public DefaultResponse(boolean success, String responseMessage) {
        this.success = success;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefaultResponse<T> res(boolean success, String responseMessage) {
        return res(success, responseMessage, null);
    }

    public static<T> DefaultResponse<T> res(boolean success, final String responseMessage, T t) {
        return DefaultResponse.<T>builder()
                .data(t)
                .success(success)
                .responseMessage(responseMessage)
                .build();
    }
}
