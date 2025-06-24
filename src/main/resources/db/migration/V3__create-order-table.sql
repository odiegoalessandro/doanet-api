CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    is_perishable BOOLEAN NOT NULL DEFAULT FALSE,
    expiration_date DATE
);

CREATE TABLE donation (
    id SERIAL PRIMARY KEY,
    donor_id INT NOT NULL REFERENCES donor(user_id),
    donation_point_id INT NOT NULL REFERENCES donation_point(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donation_item(
    id SERIAL PRIMARY KEY,
    quantity INT DEFAULT 1 NOT NULL,
    donation_id INT REFERENCES donation(id)
);