package org.koreait.survey.diabetes.repositories;

import org.koreait.survey.entities.DiabetesSurvey;
import org.springframework.data.repository.ListCrudRepository;

/**
 * 엔티티가 존재하면, 레포지토리(DB 처리) 필수!
 */
public interface DiabetesSurveyRepository extends ListCrudRepository<DiabetesSurvey, Long> {

}
