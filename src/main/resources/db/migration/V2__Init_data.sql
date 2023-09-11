INSERT INTO users(user_name, email, password, user_type, date_registered)
VALUES ('Daniel', 'daniel@test.com', 'daniel.pwd', 'ADMIN', CURRENT_TIMESTAMP),
       ('Jack', 'jack@test.com', 'jack.pwd', 'ADMIN', CURRENT_TIMESTAMP),
       ('July', 'july@test.com', 'july.pwd', 'ADMIN', CURRENT_TIMESTAMP),
       ('George', 'george@test.com', 'george.pwd', 'ADMIN', CURRENT_TIMESTAMP),
       ('Susan', 'susan@test.com', 'susan.pwd', 'ADMIN', CURRENT_TIMESTAMP);

INSERT INTO categories (category_name, description)
VALUES ('Electronics', 'Camera, Cell Phones, Accessories...'),
       ('Computers', 'Laptop, Monitors, Printers...'),
       ('Smart Home', 'Lighting, Locks, Heating and Cooling...'),
       ('Luggage', 'Backpacks, Suitcases, Duffles...'),
       ('Software', 'Games, Music, Operating Systems...');

INSERT INTO addresses (user_id, street, city, state, zip_code, country)
VALUES (1, 'Wanniassa', 'Canberra', 'ACT', '2903', 'AU'),
       (2, 'Ipswich Rd', 'Moorooka', 'QLD', '4105', 'AU'),
       (3, '566 Bridge Rd', 'Richmond', 'VIC', '3121', 'AU'),
       (4, '22 Church St', 'Burwood', 'NSW', '2134', 'AU'),
       (5, '1287 Albany Hwy', 'Cannington', 'WA', '6107', 'AU');