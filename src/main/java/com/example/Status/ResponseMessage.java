package com.example.Status;

public enum ResponseMessage {
    LOGIN_SUCCESS("로그인 성공"),
    NOT_INFORMATION("정보 없음"),
    LOGIN_FAIL("로그인 실패"),
    CREATE_MEMBER("회원 가입 성공"),
    UPDATE_MEMBER("회원 정보 수정 성공"),
    DELETE_MEMBER("회원 탈퇴 성공"),
    INTERNAL_SERVER_ERROR("서버 내부 에러"),
    DB_ERROR("데이터베이스 에러");

    private String message;

    ResponseMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
