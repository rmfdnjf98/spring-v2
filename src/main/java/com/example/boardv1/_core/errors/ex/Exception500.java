package com.example.boardv1._core.errors.ex;

// 서버 측 에러
public class Exception500 extends RuntimeException {

    public Exception500(String message) {
        super(message);
    }

}
