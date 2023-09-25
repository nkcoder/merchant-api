INSERT INTO users(user_name, email, password, user_type, date_registered)
VALUES ('Daniel', 'daniel@test.com', '$2a$10$G1veikDInr5jUC2CY5qr1eSND1A5l2sM7BH9Ml9d.nrGljW8tNOe6', 'ADMIN',
        CURRENT_TIMESTAMP),
       ('Jack', 'jack@test.com', '$2a$10$NPIh1x7bcDayb.Tld/cCv.uoE6gzU1NW8u2KLHvraYYEkmiwFe0dK', 'ADMIN',
        CURRENT_TIMESTAMP),
       ('July', 'july@test.com', '$2a$10$mHdQPGAfMAZyZFzSoT1LjeexMyv1UU6Us6cN7ZWO9k4Vs407ottva', 'ADMIN',
        CURRENT_TIMESTAMP),
       ('George', 'george@test.com', '$2a$10$vtFlMl33E6U/ILatyRFcx.kskZyPueRbrFR/DlBBS6YsrKY6YYK9a', 'ADMIN',
        CURRENT_TIMESTAMP),
       ('Susan', 'susan@test.com', '$2a$10$Kt3zwgMjCQ4g2ushvM0fm.wAfLusPzqFH0bO1ATnxH7CIMgK.kPRK', 'ADMIN',
        CURRENT_TIMESTAMP);

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