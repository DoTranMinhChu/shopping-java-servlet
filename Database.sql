CREATE DATABASE [shopping-db];
GO
USE [shopping-db];
GO
CREATE TABLE users (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    fullname NVARCHAR(255) NOT NULL,
    username VARCHAR(100),
    avatar VARCHAR(255),
    email VARCHAR(100),
    phone VARCHAR(11),
    password VARCHAR(100),
    address NVARCHAR(500),
	user_role varchar(20) NOT NULL CHECK (user_role IN('customer','admin', 'seller', 'marketing')),
    created_at DATE NOT NULL,
    deleted_at DATE
);
GO
CREATE TABLE user_tokens (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    token NVARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_TokenUser FOREIGN KEY (user_id)
    REFERENCES users(id)
);
GO

CREATE TABLE categories (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE
);
GO
CREATE TABLE products (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    thumbnail_url NVARCHAR(255) NOT NULL,
    description NVARCHAR(500) NOT NULL,
    price FLOAT NOT NULL,
    percent_discount FLOAT DEFAULT 0.00,
    quantity INT NOT NULL,
    category_id INT NOT NULL,
    total_rating FLOAT,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_ProductCategory FOREIGN KEY (category_id)
    REFERENCES categories(id)
);
GO
CREATE TABLE product_image (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    image_url NVARCHAR(255) NOT NULL,
    product_id INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_ProductImageProduct FOREIGN KEY (product_id)
    REFERENCES products(id)
);
GO
CREATE TABLE colors (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    bgr_hex VARCHAR(7),
    text_hex VARCHAR(7),
    created_at DATE NOT NULL,
    deleted_at DATE
);
GO
CREATE TABLE sizes (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE
);
GO
CREATE TABLE product_color (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    color_id INT NOT NULL,
    product_id INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_ProductColorColors FOREIGN KEY (color_id)
    REFERENCES colors(id),
    CONSTRAINT FK_ProductColorProducts FOREIGN KEY (product_id)
    REFERENCES products(id)
);
CREATE TABLE product_size (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    size_id INT NOT NULL,
	product_id INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_ProductSizeSizes FOREIGN KEY (size_id)
    REFERENCES sizes(id),
    CONSTRAINT FK_ProductSizeProducts FOREIGN KEY (product_id)
    REFERENCES products(id)
);
GO
CREATE TABLE orders (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    order_status varchar(20) NOT NULL CHECK (order_status IN('pending', 'delivering', 'user_cancel', 'shop_cancel','done')),
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_OrderUser FOREIGN KEY (user_id)
    REFERENCES users(id)
);
GO
CREATE TABLE order_detail (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
	product_description NVARCHAR(255), 
    quantity INT NOT NULL,
    total_price FLOAT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_OrderDetailOrder FOREIGN KEY (order_id)
    REFERENCES orders(id),
    CONSTRAINT FK_OrderDetailProduct FOREIGN KEY (product_id)
    REFERENCES products(id)
);
GO
CREATE TABLE feedback (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    order_detail_id INT NOT NULL,
    content NVARCHAR(255),
    rating INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_FeedbackOrderDetail FOREIGN KEY (order_detail_id)
    REFERENCES order_detail(id)
);
GO

-- Generate mock data for the users table
INSERT INTO users ( fullname, username, avatar, email, phone, password,user_role, address, created_at, deleted_at)
VALUES
    ('John Doe', 'johndoe', 'assets/images/avatar1.jpg', 'johndoe@example.com', '1234567890', 'password1','customer', '123 Main St', '2023-05-13T00:00:00+07:00', NULL),
    ('Jane Smith', 'janesmith', 'assets/images/avatar2.jpg', 'janesmith@example.com', '9876543210', 'password2','customer', '456 Elm St', '2023-05-13T00:00:00+07:00', NULL);
GO

-- Generate mock data for the categories table
INSERT INTO categories (name, created_at, deleted_at)
VALUES
    ('Clothing', '2023-05-13T00:00:00+07:00', NULL),
    ('Electronics', '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the products table
INSERT INTO products (name, thumbnail_url, description, price, percent_discount, quantity, category_id, total_rating, created_at, deleted_at)
VALUES
    ('T-Shirt 1', 'assets/images/thumbnail1.jpg', 'High-quality cotton t-shirt', 19.99, 0.1, 100,  1, 4.5, '2023-05-13T00:00:00+07:00', NULL),
    ('T-Shirt 2', 'assets/images/thumbnail2.jpg', 'High-quality cotton t-shirts', 499.99, 0.05, 50, 2, 4.8, '2023-05-13T00:00:00+07:00', NULL),
	('T-Shirt 3', 'assets/images/thumbnail1.jpg', 'High-quality cotton t-shirt', 19.99, 0.1, 100, 1, 4.5, '2023-05-13T00:00:00+07:00', NULL),
    ('T-Shirt 4', 'assets/images/thumbnail2.jpg', 'High-quality cotton t-shirts', 499.99, 0.05, 50, 2, 4.8, '2023-05-13T00:00:00+07:00', NULL),
	('T-Shirt 5', 'assets/images/thumbnail1.jpg', 'High-quality cotton t-shirt', 19.99, 0.1, 100, 1, 4.5, '2023-05-13T00:00:00+07:00', NULL),
    ('T-Shirt 6', 'assets/images/thumbnail2.jpg', 'High-quality cotton t-shirt', 499.99, 0.05, 5, 2, 4.8, '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the product_image table
INSERT INTO product_image (image_url, product_id, created_at, deleted_at)
VALUES
    ('assets/images/image1.jpg', 1, '2023-05-13T00:00:00+07:00', NULL),
    ('assets/images/image2.jpg', 1, '2023-05-13T00:00:00+07:00', NULL),
    ('assets/images/image3.jpg', 2, '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the color table
INSERT INTO colors (name, bgr_hex, text_hex, created_at, deleted_at)
VALUES
    ('Red', '#FF0000', '#FFFFFF', '2023-05-13T00:00:00+07:00', NULL),
    ('Blue', '#0000FF', '#FFFFFF', '2023-05-13T00:00:00+07:00', NULL);

-- Generate mock data for the product_color table
INSERT INTO product_color (color_id, product_id, created_at, deleted_at)
VALUES
    (1, 1, '2023-05-13T00:00:00+07:00', NULL),
    (2, 1, '2023-05-13T00:00:00+07:00', NULL),
    (1, 2, '2023-05-13T00:00:00+07:00', NULL);
GO
INSERT INTO sizes (name, created_at, deleted_at)
VALUES ('Small', '2023-05-23T00:00:00+07:00', NULL),
	   ('Medium', '2023-05-23T00:00:00+07:00', NULL),
	   ('Large', '2023-05-23T00:00:00+07:00', NULL);
	   
	   
INSERT INTO product_size (size_id, product_id, created_at, deleted_at)
VALUES (1, 1, '2023-05-23T00:00:00+07:00', NULL),
	   (2, 1, '2023-05-23T00:00:00+07:00', NULL),
	   (1, 2, '2023-05-23T00:00:00+07:00', NULL),
	   (3, 2, '2023-05-23T00:00:00+07:00', NULL);
-- Generate mock data for the orders table
INSERT INTO orders (user_id, order_status, created_at, deleted_at)
VALUES
    (1, 'pending', '2023-05-13T00:00:00+07:00', NULL),
    (2, 'delivering', '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the order_detail table
INSERT INTO order_detail (order_id, product_id, product_description, quantity, total_price, created_at, deleted_at)
VALUES
    (1, 1,'Color: Red', 2, 39.98, '2023-05-13T00:00:00+07:00', NULL),
    (2, 2,'Color: Red', 1, 499.99, '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the feedback table
INSERT INTO feedback (order_detail_id, content, rating, created_at, deleted_at)
VALUES
    (1, 'Great product!', 5, '2023-05-13T00:00:00+07:00', NULL),
    (2, 'Excellent service!', 4, '2023-05-13T00:00:00+07:00', NULL);
GO


CREATE TABLE posts (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    thumbnail_url NVARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
	title NVARCHAR(255) NOT NULL,
	sort_description NVARCHAR(500) NOT NULL,
	content NVARCHAR(2000) NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    CONSTRAINT FK_PostUser FOREIGN KEY (user_id)
    REFERENCES users(id)
);
GO

INSERT INTO users ( fullname, username, avatar, email, phone, password,user_role, address, created_at, deleted_at)
VALUES
    ('Marketing', 'marketing', 'assets/images/avatar1.jpg', 'marketing@example.com', '1234567891', 'password1','marketing', '1234 Main St', '2023-05-13T00:00:00+07:00', NULL)
GO

INSERT INTO posts (thumbnail_url, user_id, title, sort_description, content, created_at)
VALUES 
    ('https://images04.nicepage.com/feature/583347/blog-category.jpg', 3, 'Post 1', 'Short description of post 1', 'Content of post 1', '2023-05-01'),
    ('https://images04.nicepage.com/feature/583347/blog-category.jpg', 3, 'Post 2', 'Short description of post 2', 'Content of post 2', '2023-05-02'),
    ('https://images04.nicepage.com/feature/583347/blog-category.jpg', 3, 'Post 3', 'Short description of post 3', 'Content of post 3', '2023-05-03'),
    ('https://images04.nicepage.com/feature/583347/blog-category.jpg', 3, 'Post 4', 'Short description of post 4', 'Content of post 4', '2023-05-04'),
    ('https://images04.nicepage.com/feature/583347/blog-category.jpg', 3, 'Post 5', 'Short description of post 5', 'Content of post 5', '2023-05-05');