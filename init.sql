CREATE TABLE IF NOT EXISTS document (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    upload_date TIMESTAMP NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    file_size INTEGER NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    tags VARCHAR(255)[],
    version INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS tag (
    name VARCHAR(255) PRIMARY KEY
);