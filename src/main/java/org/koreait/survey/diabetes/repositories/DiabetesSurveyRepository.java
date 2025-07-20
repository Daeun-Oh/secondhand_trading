package org.koreait.survey.diabetes.repositories;

import org.koreait.survey.diabetes.entities.DiabetesSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 엔티티가 존재하면, 레포지토리(DB 처리) 필수!
 */
public interface DiabetesSurveyRepository
        extends JpaRepository<DiabetesSurvey, Long>,
                QuerydslPredicateExecutor<DiabetesSurvey> {


}
