package org.koreait.survey.diabetes.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.constants.Gender;
import org.koreait.global.exceptions.UnAuthorizedException;
import org.koreait.global.search.CommonSearch;
import org.koreait.global.search.ListData;
import org.koreait.global.search.Pagination;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.survey.diabetes.constants.SmokingHistory;
import org.koreait.survey.diabetes.entities.DiabetesSurvey;
import org.koreait.survey.diabetes.entities.QDiabetesSurvey;
import org.koreait.survey.diabetes.repositories.DiabetesSurveyRepository;
import org.koreait.survey.exceptions.SurveyNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Lazy
@Service
@Transactional  // 메서드에도 직접 사용 가능하긴 함
@RequiredArgsConstructor
public class DiabetesSurveyInfoService {

    private final MemberUtil memberUtil;
    private final HttpServletRequest request;
    private final JPAQueryFactory queryFactory;
    private final DiabetesSurveyRepository repository;

    /**
     * 설문지 1개 조회
     * - 본인이 작성한 설문지만 조회
     * - 로그인한 회원이 관리자면 상관 없이 조회 가능
     *
     * @param seq
     * @return
     */
    public DiabetesSurvey get(Long seq) {
        DiabetesSurvey item = repository.findById(seq).orElseThrow(SurveyNotFoundException::new);

        Member loggedMember = memberUtil.getMember(); // 로그인한 회원 정보
        if (!memberUtil.isLogin() || (!memberUtil.isAdmin() && !loggedMember.getSeq().equals(item.getMember().getSeq()))) {
            // 로그인 상태가 아니거나, 일반 회원(관리자X)이면서 설문지 작성한 회원과 일치하지 않을 때
            throw new UnAuthorizedException();
        }

        return item;
    }

    public ListData<DiabetesSurvey> getList(CommonSearch search) {

        if (!memberUtil.isLogin()) {
            return new ListData<>();
        }

        // -- Pagination Setting S --
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 10 : limit;
        int offset = (page - 1) * limit;  // 레코드 시작 번호
        // -- Pagination Setting E --

        Member loggedMember = memberUtil.getMember();  // 현재 로그인한 회원 정보
        QDiabetesSurvey diabetesSurvey = QDiabetesSurvey.diabetesSurvey;

        // 검색에 대한 추가적인 조건 설정을 위해 이걸 사용하겠다!
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(diabetesSurvey.member.eq(loggedMember));

        List<DiabetesSurvey> items = queryFactory.selectFrom(diabetesSurvey)
                .leftJoin(diabetesSurvey.member)
                .fetchJoin()
                .where(andBuilder)
                .orderBy(diabetesSurvey.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        long total = repository.count(andBuilder);

        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    private DiabetesSurvey mapper(ResultSet rs, int i) throws SQLException {
        DiabetesSurvey item = new DiabetesSurvey();
        item.setSeq(rs.getLong("seq"));
//        item.setMemberSeq(rs.getLong("memberSeq"));
        item.setGender(Gender.valueOf(rs.getString("gender")));
        item.setAge(rs.getInt("age"));
        item.setDiabetes(rs.getBoolean("diabetes"));
        item.setBmi(rs.getDouble("bmi"));
        item.setHeight(rs.getDouble("height"));
        item.setWeight(rs.getDouble("weight"));
        item.setHypertension(rs.getBoolean("hypertension"));
        item.setHeartDisease(rs.getBoolean("heartDisease"));
        item.setHbA1c(rs.getDouble("hbA1c"));
        item.setBloodGlucoseLevel(rs.getDouble("bloodGlucoseLevel"));
        item.setSmokingHistory(SmokingHistory.valueOf(rs.getString("smokingHistory")));

        item.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());

        Member member = new Member();
        member.setSeq(rs.getLong("memberSeq"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setMobile(rs.getString("mobile"));

        item.setMember(member);

        return item;
    }
}
