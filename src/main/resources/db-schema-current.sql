create table users
(
    id              integer   default nextval('users_user_id_seq'::regclass) not null
        primary key,
    user_name       varchar(255)                                             not null,
    email           varchar(255)                                             not null
        unique,
    password        varchar(255)                                             not null,
    date_registered timestamp default CURRENT_TIMESTAMP
);

create table orders
(
    id                  integer   default nextval('orders_order_id_seq'::regclass) not null
        primary key,
    buyer_id            integer references users,
    date_placed         timestamp default CURRENT_TIMESTAMP,
    total_amount        numeric(10, 2)                                             not null,
    shipping_address_id integer
);

create table products
(
    id           integer default nextval('products_product_id_seq'::regclass) not null
        primary key,
    seller_id    integer
        references users,
    product_name varchar(255)                                                 not null,
    description  text,
    price        numeric(10, 2)                                               not null,
    quantity     integer                                                      not null
);

create table orders_products
(
    id         serial primary key,
    order_id   integer
        references orders
            on delete cascade,
    product_id integer
        references products
            on delete cascade
);

