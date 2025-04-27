    CREATE TABLE users (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        password_hash VARCHAR(255) NOT NULL,
        phone VARCHAR(20),

        user_type VARCHAR(20) CHECK (user_type IN ('DONOR', 'ONG', 'DONATION_POINT')) NOT NULL,

        street VARCHAR(255),
        number VARCHAR(10),
        complement VARCHAR(100),
        neighborhood VARCHAR(100),
        city VARCHAR(100),
        state VARCHAR(2),
        zip_code VARCHAR(10),

        latitude DECIMAL(9,6),
        longitude DECIMAL(9,6),

        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

    CREATE TABLE donor (
        user_id INT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
        document VARCHAR(14) UNIQUE,
        reason_social VARCHAR(255)
    );

    CREATE TABLE ong (
        user_id INT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
        cnpj VARCHAR(14) UNIQUE
    );

    CREATE TABLE donation_point (
        user_id INT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
        description VARCHAR(255)
    );

