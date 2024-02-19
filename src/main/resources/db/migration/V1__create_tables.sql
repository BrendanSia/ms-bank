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
  ('Jane Smith', 25);

INSERT INTO account (cust_id, acc_no) VALUES
  ((SELECT id FROM customer WHERE name = 'John Doe'), '123456789'),
  ((SELECT id FROM customer WHERE name = 'Jane Smith'), '987654321');