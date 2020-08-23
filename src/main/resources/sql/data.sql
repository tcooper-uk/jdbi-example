CREATE TABLE services
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(100)          NOT NULL
);

CREATE TABLE organisation
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(150)          NOT NULL
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    user_name  VARCHAR(50)           NOT NULL,
    email      VARCHAR(50)           NOT NULL,
    service_id BIGINT REFERENCES services (id),
    org_id     BIGINT REFERENCES organisation (id),
    UNIQUE (user_name),
    UNIQUE (email)
);

ALTER TABLE organisation
    ADD COLUMN primary_contact BIGINT REFERENCES users (id);

INSERT INTO services(name)
VALUES ('GOLD'),
       ('SILVER'),
       ('BRONZE');

INSERT INTO users (user_name, email, service_id, org_id)
VALUES ('tcooper', 'tcooper.uk@gmail.com', 1, NULL),
       ('ssmith', 'savannah.a.smith1311@gmail.com', 2, NULL);

INSERT INTO organisation (name, primary_contact)
VALUES ('starling', 1),
       ('meridien modena', 2);

UPDATE users
SET org_id = 1
WHERE email = 'tcooper.uk@gmail.com';
UPDATE users
SET org_id = 2
WHERE email = 'savannah.a.smith1311@gmail.com';