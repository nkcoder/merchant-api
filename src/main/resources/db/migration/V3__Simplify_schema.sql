-- drop unnecessary tables at the moment
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS order_details CASCADE;
DROP TABLE IF EXISTS payments CASCADE;

-- update `users` table
ALTER TABLE users
    DROP COLUMN user_type CASCADE;

ALTER TABLE users
    RENAME user_id TO id;

-- update `products` table
ALTER TABLE products
    DROP COLUMN category_id CASCADE,
DROP
COLUMN image_url CASCADE;

ALTER TABLE products
    RENAME COLUMN stock_quantity TO quantity;

ALTER TABLE products
    RENAME product_id to id;

ALTER TABLE products
DROP
CONSTRAINT products_seller_id_fkey;

ALTER TABLE products
    ADD CONSTRAINT products_seller_id_fkey FOREIGN KEY (seller_id) REFERENCES users (id);

-- update `orders` table
ALTER TABLE orders
    DROP COLUMN billing_address_id CASCADE,
DROP
COLUMN payment_id CASCADE;

ALTER TABLE orders
    RENAME order_id TO id;

ALTER TABLE orders
DROP
CONSTRAINT orders_buyer_id_fkey;

ALTER TABLE orders
    ADD CONSTRAINT orders_buyer_id_fkey FOREIGN KEY (buyer_id) REFERENCES users (id);

-- add a join table between `orders` and `products`
CREATE TABLE orders_products
(
    id         SERIAL PRIMARY KEY,
    order_id   INTEGER REFERENCES orders (id) ON DELETE CASCADE,
    product_id INTEGER REFERENCES products (id) ON DELETE CASCADE
);

-- init data for `products`
INSERT INTO products(seller_id, product_name, description, price, quantity)
VALUES (1, 'iPhone', 'A great phone', 2000, 20),
       (2, 'Tesla', 'The smartest car', 65000, 30),
       (3, 'GPT-4', 'The best model from OpenAI', 1500, 100),
       (4, 'Effective Java (3rd)', 'Awesome book about Java', 40, 5000),
       (5, 'Functional Programming in Scala (2nd)', 'Great book about FP', 50, 30000);

