package com.example.Status;

public class JsonResponse {

    public static FailureResponse fail(boolean success, String message, String errorCode) {
        return FailureResponse.builder()
                .errorCode(errorCode)
                .success(success)
                .message(message)
                .build();
    }

    public static<T> SuccessResponse<T> ok(boolean success, String message) {
        return ok(success, message, null);
    }



    public static<T> SuccessResponse<T> ok(boolean success, final String message, T t) {
        return SuccessResponse.<T>builder()
                .data(t)
                .success(success)
                .message(message)
                .build();
    }
}
