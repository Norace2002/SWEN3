CREATE TABLE IF NOT EXISTS document (
    id uuid PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    upload_date TIMESTAMP NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    file_size INTEGER NOT NULL,
    ocr_readable BOOLEAN NOT NULL DEFAULT FALSE
);