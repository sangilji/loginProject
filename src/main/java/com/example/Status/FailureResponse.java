package com.example.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class FailureResponse {

    private final boolean success;
    private final String message;
    private final String errorCode;


}
