CREATE TABLE customer (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(255) NOT NULL,
    age INT
);

CREATE TABLE account (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    cust_id UNIQUEIDENTIFIER NOT NULL,
    acc_no NVARCHAR(20) NOT NULL,
    FOREIGN KEY (cust_id) REFERENCES customer(id)
);

INSERT INTO customer (name, age) VALUES
  ('John Doe', 30),
  ('Jane Smith', 25),
  ('Michael Johnson', 35),
  ('Emily Brown', 28),
  ('David Wilson', 40),
  ('Emma Martinez', 32),
  ('William Taylor', 29),
  ('Olivia Garcia', 38),
  ('James Anderson', 45),
  ('Sophia Hernandez', 27),
  ('Benjamin Thomas', 33),
  ('Ava Lopez', 42);

INSERT INTO account (cust_id, acc_no) VALUES
  ((SELECT id FROM customer WHERE name = 'John Doe'), '123456789'),
  ((SELECT id FROM customer WHERE name = 'Jane Smith'), '987654321'),
  ((SELECT id FROM customer WHERE name = 'Michael Johnson'), '456789123'),
  ((SELECT id FROM customer WHERE name = 'Emily Brown'), '789123456'),
  ((SELECT id FROM customer WHERE name = 'David Wilson'), '321654987'),
  ((SELECT id FROM customer WHERE name = 'Emma Martinez'), '654987321'),
  ((SELECT id FROM customer WHERE name = 'William Taylor'), '987321654'),
  ((SELECT id FROM customer WHERE name = 'Olivia Garcia'), '654123789'),
  ((SELECT id FROM customer WHERE name = 'James Anderson'), '789321654'),
  ((SELECT id FROM customer WHERE name = 'Sophia Hernandez'), '123987456'),
  ((SELECT id FROM customer WHERE name = 'Benjamin Thomas'), '456321789'),
  ((SELECT id FROM customer WHERE name = 'Ava Lopez'), '321789654');