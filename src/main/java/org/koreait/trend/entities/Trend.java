package org.koreait.trend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Lob;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.springframework.data.annotation.Id;

@Data
@Entity
public class Trend extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 50)
    private String category;

    @Lob
    private String wordCloud;

    @Lob
    private String keywords;
}
