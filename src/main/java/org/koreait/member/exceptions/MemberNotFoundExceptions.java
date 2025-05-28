package org.koreait.member.exceptions;

import org.koreait.global.exceptions.NotFoundException;

public class MemberNotFoundExceptions extends NotFoundException {
    public MemberNotFoundExceptions() {
        super("NotFound.member");
        setErrorCode(true);
    }
}
