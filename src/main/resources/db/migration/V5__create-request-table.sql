CREATE TABLE request (
    id SERIAL PRIMARY KEY,
    donation_point_id INT NOT NULL REFERENCES donation_point(user_id),
    ong_id INT NOT NULL REFERENCES ong(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE request_item(
    id SERIAL PRIMARY KEY,
    request_id INT NOT NULL REFERENCES request(id),
    item_id INT NOT NULL REFERENCES item(id),
    quantity INT DEFAULT 1 NOT NULL
);