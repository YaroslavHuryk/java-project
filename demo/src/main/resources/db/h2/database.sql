DROP TABLE customers IF EXISTS;

CREATE TABLE customers (
  id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  email      VARCHAR(100));

CREATE INDEX users_last_name ON customers (last_name);
