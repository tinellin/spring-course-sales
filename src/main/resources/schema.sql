CREATE TABLE Client (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Product (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    description VARCHAR(100) NOT NULL,
    unit_price NUMERIC(20, 2)
);

CREATE TABLE Purchase_Order (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    client_id INTEGER,
    order_data TIMESTAMP,
    total NUMERIC(20, 2),

    FOREIGN KEY (client_id) REFERENCES Client(id)
);

CREATE TABLE Order_Item (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    quantity INTEGER NOT NULL,
    order_id INTEGER,
    product_id INTEGER,

    FOREIGN KEY (order_id) REFERENCES Purchase_Order(id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);