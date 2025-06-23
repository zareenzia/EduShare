CREATE TABLE file_metadata (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    course_name    VARCHAR(255) NOT NULL,
    course_code    VARCHAR(100) NOT NULL,
    instructor     VARCHAR(255) NOT NULL,
    department     VARCHAR(255),
    semester       VARCHAR(100) NOT NULL,
    tags           VARCHAR(500),
    file_name      VARCHAR(255) NOT NULL,
    file_type      VARCHAR(100),
    file_size      BIGINT,
    uploaded_at    DATETIME,
    gcs_url        VARCHAR(500)
);


--User table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
