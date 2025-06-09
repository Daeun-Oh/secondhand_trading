package org.koreait.trend.exceiptions;

import org.koreait.global.exceptions.NotFoundException;

public class TrendNotFoundException extends NotFoundException {
    public TrendNotFoundException() {
        super("NotFound.trend");
    }
}
