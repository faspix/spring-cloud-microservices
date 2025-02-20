--liquibase formatted sql

--changeset faspix:1
CREATE TABLE IF NOT EXISTS account (
    account_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

-- changeset faspix:2
CREATE TABLE IF NOT EXISTS account_bill (
    account_id BIGINT NOT NULL,
    bill_id BIGINT NOT NULL,
    CONSTRAINT fk_accont_id FOREIGN KEY (account_id)
        REFERENCES account (account_id)
);