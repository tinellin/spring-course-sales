CREATE TABLE Client (
     id INTEGER PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(100),
     cpf CHAR(11)
);

CREATE TABLE Product (
     id INTEGER PRIMARY KEY AUTO_INCREMENT,
     description VARCHAR(100),
     price NUMERIC(20,2)
);

CREATE TABLE Purchase_Order (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    client_id INTEGER REFERENCES Client (id),
    order_data TIMESTAMP,
    status VARCHAR(20),
    total NUMERIC(20,2)
);

CREATE TABLE OrderItem (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    order_id INTEGER REFERENCES Purchase_Order (id),
    product_id INTEGER REFERENCES Product (id),
    quantity INTEGER
);

CREATE TABLE User(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    admin BOOL DEFAULT FALSE
);