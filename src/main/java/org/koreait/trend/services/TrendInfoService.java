package org.koreait.trend.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.search.CommonSearch;
import org.koreait.trend.entities.Trend;
import org.koreait.trend.exceiptions.TrendNotFoundException;
import org.koreait.trend.repositories.TrendRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class TrendInfoService {

    private final TrendRepository repository;

    /**
     * 최근 트렌드 1개 조회
     *
     * @param category
     * @return
     */
    public Trend getLatest(String category) {
        Trend item = repository.getLatest(category).orElseThrow(TrendNotFoundException::new);

        return item;
    }

    /**
     * 특정 날짜의 트렌드 데이터 1개
     * To-do
     *
     * @param date
     * @return
     */
    public Trend get(String category, LocalDate date) {
        return null;
    }

    /**
     * 특정 날짜 범위의 트렌드, 데이터 조회 (목록으로 불러오기)
     * To-do
     *
     * @return
     */
    public List<Trend> getList(String category, CommonSearch search) {
        return null;
    }
}
