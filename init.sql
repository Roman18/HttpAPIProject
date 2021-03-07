CREATE SCHEMA contact_book;


CREATE TABLE users(
id SERIAL PRIMARY KEY,
login VARCHAR(30) NOT NULL,
password VARCHAR(30) NOT NULL,
date_born DATE NOT NULL
);

CREATE TABLE contacts(
id SERIAL PRIMARY KEY,
"name" VARCHAR(30) NOT NULL,
contact VARCHAR(30) NOT NULL,
type_contact CHAR(5) NOT NULL,
users_id INT NOT NULL,
CONSTRAINT contacts_users_id_fk FOREIGN KEY(users_id) REFERENCES users(id)
);
