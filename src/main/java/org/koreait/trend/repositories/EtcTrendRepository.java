package org.koreait.trend.repositories;

import org.koreait.trend.entities.EtcTrend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EtcTrendRepository extends ListCrudRepository<EtcTrend, Long> {

    // 1. 기간 내 siteUrl 기준 최신 1건 조회 (페이지 사이즈 1짜리 Pageable 전달)
    Page<EtcTrend> findBySiteUrlAndCreatedAtBetweenOrderByCreatedAtDesc(
            String siteUrl,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    );

    // 2. 기간 내 siteUrl 기준 전체 조회 (생성일 오름차순)
    List<EtcTrend> findBySiteUrlAndCreatedAtBetweenOrderByCreatedAtAsc(
            String siteUrl,
            LocalDateTime from,
            LocalDateTime to
    );

    // 3. 중복 없는 siteUrl 목록 조회
    List<String> findDistinctSiteUrlByCategoryIsNotNull();

}
