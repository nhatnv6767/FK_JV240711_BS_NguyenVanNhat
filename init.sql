CREATE DATABASE IF NOT EXISTS shopping_management_dk;
USE shopping_management_dk;

CREATE TABLE Categories(
                           category_id INT PRIMARY KEY AUTO_INCREMENT,
                           category_name VARCHAR(50) NOT NULL UNIQUE,
                           category_status BIT DEFAULT 1
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE Products(
                         product_id INT PRIMARY KEY AUTO_INCREMENT,
                         product_name VARCHAR(20) NOT NULL UNIQUE,
                         stock INT NOT NULL,
                         cost_price DECIMAL(10,2) NOT NULL CHECK (cost_price > 0),
                         selling_price DECIMAL(10,2) NOT NULL CHECK (selling_price > 0),
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         category_id INT NOT NULL,
                         FOREIGN KEY (category_id) REFERENCES Categories(category_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Insert initial data
INSERT INTO Categories (category_name, category_status) VALUES ('Electronics', 1);
INSERT INTO Products (product_name, stock, cost_price, selling_price, created_at, category_id) VALUES
    ('iPhone 13', 100, 700.00, 1000.00, '2023-05-01 10:00:00', 1);