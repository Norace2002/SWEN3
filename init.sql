CREATE TABLE IF NOT EXISTS document (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    file_url VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tags (
    tag VARCHAR(255) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS metadata (
    id VARCHAR(255) PRIMARY KEY REFERENCES documents(id) ON DELETE CASCADE,
    author VARCHAR(255) NOT NULL,
    upload_date VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    file_size INTEGER NOT NULL,
    document_tags VARCHAR(255)[],
    version INTEGER NOT NULL
);