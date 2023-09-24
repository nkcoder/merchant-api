-- drop unnecessary tables at the moment
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS order_details CASCADE;
DROP TABLE IF EXISTS payments CASCADE;

-- update `users` table
ALTER TABLE users
    DROP COLUMN user_type CASCADE;

-- update `products` table
ALTER TABLE products
    DROP COLUMN category_id CASCADE,
    DROP COLUMN image_url CASCADE;

ALTER TABLE products
    RENAME COLUMN stock_quantity TO quantity;

-- update `orders` table
ALTER TABLE orders
    DROP COLUMN billing_address_id CASCADE,
    DROP COLUMN payment_id CASCADE;

-- add a join table between `orders` and `products`
CREATE TABLE orders_products
(
    order_id   INTEGER REFERENCES orders (order_id) ON DELETE CASCADE,
    product_id INTEGER REFERENCES products (product_id) ON DELETE CASCADE
);

