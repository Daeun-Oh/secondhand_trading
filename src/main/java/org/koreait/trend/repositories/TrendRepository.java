package org.koreait.trend.repositories;

import org.koreait.trend.entities.Trend;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrendRepository extends ListCrudRepository<Trend, Long> {
    // 최신 1건
    Optional<Trend> findFirstByCategoryOrderByCreatedAtDesc(String category);

    // 기간 내 리스트
    List<Trend> findByCategoryAndSiteUrlAndCreatedAtBetweenOrderByCreatedAtAsc(
            String category,
            String siteUrl,
            LocalDateTime sDate,
            LocalDateTime eDate
    );

}
