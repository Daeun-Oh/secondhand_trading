package org.koreait.survey.diabetes.constants;

public enum SmokingHistory {
    NO_INFO(0),  // 정보 없음
    CURRENT(1),  // 현재 흡연 중
    EVER(2),     // 항상 흡연 중
    FORMER(3),   // 과거 흡연했지만 지금은 X
    NEVER(4),    // 비흡연자
    NOT_CURRENT(5);     // 현재는 아니지만 흡연 예정

    private final int num;

    SmokingHistory(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
