package org.koreait.survey.diabetes.controllers;

import lombok.Data;
import org.koreait.global.constants.Gender;
import org.koreait.survey.diabetes.constants.SmokingHistory;

@Data
public class RequestDiabetesSurvey {
    private String mode;                        // step1, step2 구분 (검증을 달리 하기 위함)
    private Gender gender;                      // 성별
    private int age;                            // 나이
    private boolean hypertension;               // 고혈압 여부
    private boolean heartDisease;               // 심장질환 여부
    private SmokingHistory smokingHistory;      // 흡연 여부
    private double height;                      // 키(cm)
    private double weight;                      // 몸무게(kg) -> BMI 지수 계산
    private double hbA1c;                       // 당화혈색소 수치 (2~3개월 동안 평균 혈당 수치) (%)
    private double bloodGlucoseLevel;           // 혈당 수치 (mg/dL)
}
