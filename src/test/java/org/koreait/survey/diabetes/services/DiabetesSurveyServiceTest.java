package org.koreait.survey.diabetes.services;

import org.junit.jupiter.api.Test;
import org.koreait.global.constants.Gender;
import org.koreait.survey.diabetes.constants.SmokingHistory;
import org.koreait.survey.diabetes.controllers.RequestDiabetesSurvey;
import org.koreait.survey.entities.DiabetesSurvey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiabetesSurveyServiceTest {

    @Autowired
    private DiabetesSurveyService service;

    @Test
    void test() {
        RequestDiabetesSurvey form = new RequestDiabetesSurvey();
        form.setGender(Gender.FEMALE);
        form.setAge(62);
        form.setHypertension(true);
        form.setHeartDisease(true);
        form.setSmokingHistory(SmokingHistory.FORMER);
        form.setHeight(165.);
        form.setWeight(71.1);
        form.setHbA1c(8.8);
        form.setBloodGlucoseLevel(280);

        DiabetesSurvey item = service.process(form);
        System.out.println(item);
    }
}
