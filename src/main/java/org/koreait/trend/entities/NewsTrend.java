package org.koreait.trend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Data;

import java.util.Map;

@Data
@Entity
public class NewsTrend {
    @Lob
    private String image;

    @Lob
    private Map<String, Integer> keywords;
}
