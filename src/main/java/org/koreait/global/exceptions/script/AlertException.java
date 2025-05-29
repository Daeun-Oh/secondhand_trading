package org.koreait.global.exceptions.script;

import lombok.Getter;
import org.koreait.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

@Getter
public class AlertException extends CommonException {
    public AlertException(String message, HttpStatus status) {
        super(message, status);
    }

    public AlertException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
