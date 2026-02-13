CREATE TABLE school_profile (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    logo_url VARCHAR(255),
    contact_email VARCHAR(100),
    contact_phone VARCHAR(50),
    updated_at TIMESTAMP
);

INSERT INTO school_profile (name, address, contact_email, updated_at)
VALUES ('Default School Name', '123 School Address', 'info@school.com', NOW());
