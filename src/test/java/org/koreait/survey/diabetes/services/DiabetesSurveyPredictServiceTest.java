package org.koreait.survey.diabetes.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.koreait.global.constants.Gender;
import org.koreait.survey.diabetes.constants.SmokingHistory;
import org.koreait.survey.diabetes.controllers.RequestDiabetesSurvey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DiabetesSurveyPredictServiceTest {

    @Autowired
    private DiabetesSurveyPredictService service;

    @Autowired
    private ObjectMapper om;

    @Test
    void test() throws Exception {
        String json = "[[0.0, 13.0, 0.0, 0.0, 0.0, 24.11, 5.0, 140.0], [0.0, 46.0, 0.0, 0.0, 1.0, 27.32, 6.1, 200.0], [0.0, 41.0, 0.0, 0.0, 4.0, 33.94, 6.0, 140.0], [1.0, 65.0, 1.0, 0.0, 4.0, 30.65, 5.7, 140.0], [1.0, 69.0, 1.0, 0.0, 3.0, 33.81, 5.8, 160.0]]";


        List<List<Number>> items = om.readValue(json, new TypeReference<List<List<Number>>>() {});  // TypeReference에서 List<List<Number>> 생략 가능

        List<Integer> results = service.process(items);
        System.out.println(results);
    }

    @Test
    void test2() {
        List<Number> item = List.of(0.  ,  62.  ,   1.  ,   1.  ,   3.  ,  26.11,   8.8 , 280.);

        boolean result = service.isDiabetes(item);
        System.out.println(result);
    }

    @Test
    void test3() {
        // test2의 item과 같은 정보를 form을 통해 전달

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

        boolean result = service.isDiabetes(form);
        System.out.println(result);
    }
}
