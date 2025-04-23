CREATE TABLE file_metadata (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    course_name  VARCHAR(255) NOT NULL,
    instructor   VARCHAR(255) NOT NULL,
    semester     VARCHAR(100) NOT NULL,
    tags         VARCHAR(500),
    file_name    VARCHAR(255) NOT NULL,
    file_type    VARCHAR(100),
    file_size    BIGINT,
    uploaded_at  DATETIME,
    gcs_url      VARCHAR(500)
);
