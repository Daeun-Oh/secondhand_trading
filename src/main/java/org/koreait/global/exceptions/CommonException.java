package org.koreait.global.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CommonException extends RuntimeException {

    private final HttpStatus status;
    private boolean errorCode; // 에러 코드인지 확인 (getter, setter로 설정 및 확인)
    private Map<String, List<String>> errorMessages;

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * 커맨드 검증 실패 시 예외 처리
     * @param errorMessages 필드 이름, 해당 필드에서 발생한 에러 메시지들
     * @param status
     */
    public CommonException(Map<String, List<String>> errorMessages, HttpStatus status) {
        this.status = status;
        this.errorMessages = errorMessages;
    }
}
