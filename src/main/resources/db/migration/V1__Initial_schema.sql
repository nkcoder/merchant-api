CREATE TABLE users
(
    user_id         SERIAL PRIMARY KEY,
    user_name       VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    password        VARCHAR(255)        NOT NULL,
    user_type       VARCHAR(50)         NOT NULL,
    date_registered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    description   TEXT
);

CREATE TABLE products
(
    product_id     SERIAL PRIMARY KEY,
    seller_id      INTEGER REFERENCES users (user_id),
    category_id    INTEGER REFERENCES categories (category_id),
    product_name   VARCHAR(255)   NOT NULL,
    description    TEXT,
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INTEGER        NOT NULL,
    image_url      VARCHAR(500)
);

CREATE TABLE addresses
(
    address_id SERIAL PRIMARY KEY,
    user_id    INTEGER REFERENCES users (user_id),
    street     VARCHAR(255) NOT NULL,
    city       VARCHAR(100) NOT NULL,
    state      VARCHAR(100),
    zip_code   VARCHAR(50),
    country    VARCHAR(100) NOT NULL
);

CREATE TABLE payments
(
    payment_id     SERIAL PRIMARY KEY,
    amount         DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(255)   NOT NULL,
    date_paid      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50)    NOT NULL
);

CREATE TABLE orders
(
    order_id            SERIAL PRIMARY KEY,
    buyer_id            INTEGER REFERENCES users (user_id),
    date_placed         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount        DECIMAL(10, 2) NOT NULL,
    shipping_address_id INTEGER REFERENCES addresses (address_id),
    billing_address_id  INTEGER REFERENCES addresses (address_id),
    payment_id          INTEGER REFERENCES payments (payment_id)
);

CREATE TABLE order_details
(
    order_detail_id SERIAL PRIMARY KEY,
    order_id        INTEGER REFERENCES orders (order_id),
    product_id      INTEGER REFERENCES products (product_id),
    quantity        INTEGER        NOT NULL,
    sub_total       DECIMAL(10, 2) NOT NULL
);

CREATE TABLE reviews
(
    review_id   SERIAL PRIMARY KEY,
    product_id  INTEGER REFERENCES products (product_id),
    user_id     INTEGER REFERENCES users (user_id),
    rating      INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment     TEXT,
    date_posted TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
