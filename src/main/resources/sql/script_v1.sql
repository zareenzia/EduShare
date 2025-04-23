CREATE TABLE file_metadata
(
    id          bigint       NOT NULL auto_increment PRIMARY KEY,
    title       varchar(255) NOT NULL,
    course_name varchar(255) NOT NULL,
    instructor  varchar(255) NOT NULL,
    semester    varchar(100) NOT NULL,
    tags        varchar(500),
    file_name   varchar(255) NOT NULL,
    file_type   varchar(100),
    file_size   bigint,
    uploaded_at datetime,
    file_data   longblob
);

