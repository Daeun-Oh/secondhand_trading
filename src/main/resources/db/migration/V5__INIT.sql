CREATE TABLE RESTAURANT (
    seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    Zipcode VARCHAR(10),
    address VARCHAR(100),
    zonecode VARCHAR(10),
    roadAddress VARCHAR(100),
    category VARCHAR(30),
    name VARCHAR(100),
    lat DOUBLE,
    lon DOUBLE
);