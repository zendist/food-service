

CREATE TABLE IF NOT EXISTS food_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    price INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    balance INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS orders (
    total INTEGER NOT NULL,
    created DATE NOT NULL,
    customer_id INTEGER REFERENCES customers(id) NOT NULL,
    food_item_id INTEGER REFERENCES food_items(id) NOT NULL,
    PRIMARY KEY (created, customer_id, food_item_id));

INSERT INTO food_items VALUES
    ("Гамбургер"),
    ("Рис с овощами"),
    ("Куриная отбивная"),
    ("Пирожки с картошкой");

INSERT INTO customers VALUES
    ("John Galt", 5000),
    ("Jenny Fisher", 10000),
    ("Daniel Ming", 200),
    ("Vince Hong", 4000);
