package org.koreait.global.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonException extends RuntimeException {

    private final HttpStatus status;
    private boolean errorCode; // 에러 코드인지 확인 (getter, setter로 설정 및 확인)

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
