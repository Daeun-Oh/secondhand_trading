package org.koreait.survey.diabetes.services;

import lombok.RequiredArgsConstructor;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.survey.diabetes.controllers.RequestDiabetesSurvey;
import org.koreait.survey.diabetes.repositories.DiabetesSurveyRepository;
import org.koreait.survey.diabetes.entities.DiabetesSurvey;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class DiabetesSurveyService {

    private final DiabetesSurveyPredictService predictService;
    private final DiabetesSurveyRepository repository;
    private final MemberUtil memberUtil;
    private final ModelMapper mapper;

    public DiabetesSurvey process(RequestDiabetesSurvey form) {
        /**
         * 1. 설문 답변으로 고위험군 예측 결과 가져오기
         * 2. 로그인한 회원 정보 가져오기
         * 3. DB에 저장 처리
         */

        // 1. 설문 답변으로 고위험군 예측 결과 가져오기 (py)
        boolean diabetes = predictService.isDiabetes(form);

        // 2. 로그인한 회원 정보 가져오기
        Member member = memberUtil.getMember();

        // 3. DB에 저장 처리
        DiabetesSurvey item = mapper.map(form, DiabetesSurvey.class);  // 동일한 패턴의 getter, setter 치환

        if (memberUtil.isLogin()) {
            item.getMember().setSeq(member.getSeq());
        }
        item.setDiabetes(diabetes);
        double bmi = predictService.getBMI(form.getHeight(), form.getWeight());
        item.setBmi(bmi);

        item.setMember(member); // DB에는 안 들어감

        repository.saveAndFlush(item);

        return repository.findById(item.getSeq()).orElse(null);
    }
}
