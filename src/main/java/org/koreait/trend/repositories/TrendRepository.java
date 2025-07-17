package org.koreait.trend.repositories;

import com.querydsl.core.BooleanBuilder;
import org.koreait.trend.entities.QTrend;
import org.koreait.trend.entities.Trend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

public interface TrendRepository
        extends JpaRepository<Trend, Long>,
                QuerydslPredicateExecutor<Trend> {

    default Optional<Trend> getLatest(String category) {
        QTrend trend = QTrend.trend;
        Pageable pageable = PageRequest.of(0, 1, Sort.by(desc("createdAt")));
        Page<Trend> data = findAll(trend.category.eq(category), pageable);
        List<Trend> items = data.getContent();
        return Optional.ofNullable(items.getFirst());
    }

    default Optional<Trend> get(String category, LocalDateTime sDate, LocalDateTime eDate) {
        QTrend trend = QTrend.trend;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(trend.category.eq(category))
                .and(trend.createdAt.between(sDate, eDate));

        Pageable pageable = PageRequest.of(0, 1, Sort.by(desc("createdAt")));

        Page<Trend> data = findAll(andBuilder, pageable);
        List<Trend> items = data.getContent();

        return Optional.ofNullable(items.getFirst());
    }

    default List<Trend> getList(String category, LocalDateTime sDate, LocalDateTime eDate) {
        QTrend trend = QTrend.trend;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(trend.category.eq(category))
                .and(trend.createdAt.between(sDate, eDate));

        return (List<Trend>)findAll(andBuilder);
    }
}
