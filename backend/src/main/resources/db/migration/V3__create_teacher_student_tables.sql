CREATE TABLE teachers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    bio TEXT,
    subjects VARCHAR(255)
);

CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    grade VARCHAR(20),
    class_name VARCHAR(50),
    enrollment_year INTEGER
);
