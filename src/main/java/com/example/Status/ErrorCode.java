package com.example.Status;

public enum ErrorCode {
    E00("E00"), // 입력값이 잘못 됨
    E01("E01"), // 권한이 없음
    E02("E02"), // 로그인 안됨
    E03("E03"), // 없는 유저로 로그인 시도
    E04("E04"); // 패스워드가 실패함

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
