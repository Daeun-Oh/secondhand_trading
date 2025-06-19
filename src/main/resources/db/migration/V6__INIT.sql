CREATE TABLE SURVEY_DIABETES (
    seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    memberSeq BIGINT,
    gender ENUM('FEMALE', 'MALE', 'OTHER') DEFAULT 'FEMALE',
    age INT,
    hypertension TINYINT(1), -- 0 or 1
    heartDisease TINYINT(1), -- 0 or 1
    smokingHistory ENUM('NO_INFO', 'CURRENT', 'EVER', 'FORMER', 'NEVER', 'NOT_CURRENT') DEFAULT 'NO_INFO',
    height FLOAT,
    weight FLOAT,
    bmi FLOAT, -- BMI 지수 (직접 계산 후 저장)
    hbA1c FLOAT,
    bloodGlucoseLevel FLOAT,
    diabetes TINYINT(1), -- 당뇨 고위험군 결과 (0 or 1)
    trainDone TINYINT(1), -- 훈련 완료 여부 (0 or 1)
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    modifiedAt DATETIME,
    deletedAt DATETIME,

    FOREIGN KEY(memberSeq) REFERENCES MEMBER(seq)
);