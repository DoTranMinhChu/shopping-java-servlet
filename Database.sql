CREATE TABLE users (
    id INT NOT NULL,
    fullname NVARCHAR(255) NOT NULL,
    username VARCHAR(100),
    avatar VARCHAR(255),
    email VARCHAR(100),
    phone VARCHAR(11),
    password VARCHAR(100),
    address NVARCHAR(500),
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id)
);
GO
CREATE TABLE shops (
    id INT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    address NVARCHAR(500),
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_ShopAccount FOREIGN KEY (user_id)
    REFERENCES users(id)
);
GO
CREATE TABLE categories (
    id INT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id)
);
GO
CREATE TABLE products (
    id INT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    thumbnail_url NVARCHAR(255) NOT NULL,
    description NVARCHAR(500) NOT NULL,
    price FLOAT NOT NULL,
    percent_discount FLOAT DEFAULT 0.00,
    quantity INT NOT NULL,
    shop_id INT NOT NULL,
    category_id INT NOT NULL,
    total_rating FLOAT,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_ProductShop FOREIGN KEY (shop_id)
    REFERENCES shops(id),
    CONSTRAINT FK_ProductCategory FOREIGN KEY (category_id)
    REFERENCES categories(id)
);
GO
CREATE TABLE product_image (
    id INT NOT NULL,
    image_url NVARCHAR(255) NOT NULL,
    product_id INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_ProductImageProduct FOREIGN KEY (product_id)
    REFERENCES products(id)
);
GO
CREATE TABLE colors (
    id INT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    bgr_hex VARCHAR(7),
    text_hex VARCHAR(7),
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id)
);
GO
CREATE TABLE sizes (
    id INT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id)
);
GO
CREATE TABLE product_color (
    id INT NOT NULL,
    color_id INT NOT NULL,
    product_id INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_ProductColorColors FOREIGN KEY (color_id)
    REFERENCES colors(id),
    CONSTRAINT FK_ProductColorProducts FOREIGN KEY (product_id)
    REFERENCES products(id)
);
CREATE TABLE product_size (
    id INT NOT NULL,
    size_id INT NOT NULL,
	product_id INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_ProductSizeSizes FOREIGN KEY (size_id)
    REFERENCES sizes(id),
    CONSTRAINT FK_ProductSizeProducts FOREIGN KEY (product_id)
    REFERENCES products(id)
);
GO
CREATE TABLE orders (
    id INT NOT NULL,
    user_id INT NOT NULL,
    shop_id INT NOT NULL,
    status varchar(20) NOT NULL CHECK (status IN('pending', 'delivering', 'user_cancel', 'shop_cancel','done')),
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_OrderUser FOREIGN KEY (user_id)
    REFERENCES users(id),
    CONSTRAINT FK_OrderShop FOREIGN KEY (shop_id)
    REFERENCES shops(id)
);
GO
CREATE TABLE order_detail (
    id INT NOT NULL,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
	product_description NVARCHAR(255), 
    quantity INT NOT NULL,
    total_price FLOAT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_OrderDetailOrder FOREIGN KEY (order_id)
    REFERENCES orders(id),
    CONSTRAINT FK_OrderDetailProduct FOREIGN KEY (product_id)
    REFERENCES products(id)
);
GO
CREATE TABLE feedback (
    id INT NOT NULL,
    order_detail_id INT NOT NULL,
    content NVARCHAR(255),
    rating INT NOT NULL,
    created_at DATE NOT NULL,
    deleted_at DATE,
    PRIMARY KEY (id),
    CONSTRAINT FK_FeedbackOrderDetail FOREIGN KEY (order_detail_id)
    REFERENCES order_detail(id)
);
GO

-- Generate mock data for the users table
INSERT INTO users (id, fullname, username, avatar, email, phone, password, address, created_at, deleted_at)
VALUES
    (1, 'John Doe', 'johndoe', 'assets/images/avatar1.jpg', 'johndoe@example.com', '1234567890', 'password1', '123 Main St', '2023-05-13T00:00:00+07:00', NULL),
    (2, 'Jane Smith', 'janesmith', 'assets/images/avatar2.jpg', 'janesmith@example.com', '9876543210', 'password2', '456 Elm St', '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the shops table
INSERT INTO shops (id, name, user_id, address, created_at, deleted_at)
VALUES
    (1, 'Fashion Paradise', 1, '789 Oak St', '2023-05-13T00:00:00+07:00', NULL),
    (2, 'ElectroTech', 2, '123 Pine St', '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the categories table
INSERT INTO categories (id, name, created_at, deleted_at)
VALUES
    (1, 'Clothing', '2023-05-13T00:00:00+07:00', NULL),
    (2, 'Electronics', '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the products table
INSERT INTO products (id, name, thumbnail_url, description, price, percent_discount, quantity, shop_id, category_id, total_rating, created_at, deleted_at)
VALUES
    (1, 'T-Shirt', 'assets/images/thumbnail1.jpg', 'High-quality cotton t-shirt', 19.99, 0.1, 100, 1, 1, 4.5, '2023-05-13T00:00:00+07:00', NULL),
    (2, 'Smartphone', 'assets/images/thumbnail2.jpg', 'Powerful smartphone with advanced features', 499.99, 0.05, 50, 2, 2, 4.8, '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the product_image table
INSERT INTO product_image (id, image_url, product_id, created_at, deleted_at)
VALUES
    (1, 'assets/images/image1.jpg', 1, '2023-05-13T00:00:00+07:00', NULL),
    (2, 'assets/images/image2.jpg', 1, '2023-05-13T00:00:00+07:00', NULL),
    (3, 'assets/images/image3.jpg', 2, '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the color table
INSERT INTO colors (id, name, bgr_hex, text_hex, created_at, deleted_at)
VALUES
    (1, 'Red', '#FF0000', '#FFFFFF', '2023-05-13T00:00:00+07:00', NULL),
    (2, 'Blue', '#0000FF', '#FFFFFF', '2023-05-13T00:00:00+07:00', NULL);

-- Generate mock data for the product_color table
INSERT INTO product_color (id, color_id, product_id, created_at, deleted_at)
VALUES
    (1, 1, 1, '2023-05-13T00:00:00+07:00', NULL),
    (2, 2, 1, '2023-05-13T00:00:00+07:00', NULL),
    (3, 1, 2, '2023-05-13T00:00:00+07:00', NULL);
GO
INSERT INTO sizes (id, name, created_at, deleted_at)
VALUES (1, 'Small', '2023-05-23T00:00:00+07:00', NULL),
	   (2, 'Medium', '2023-05-23T00:00:00+07:00', NULL),
	   (3, 'Large', '2023-05-23T00:00:00+07:00', NULL);
	   
	   
INSERT INTO product_size (id, size_id, product_id, created_at, deleted_at)
VALUES (1, 1, 1, '2023-05-23T00:00:00+07:00', NULL),
	   (2, 2, 1, '2023-05-23T00:00:00+07:00', NULL),
	   (3, 1, 2, '2023-05-23T00:00:00+07:00', NULL),
	   (4, 3, 2, '2023-05-23T00:00:00+07:00', NULL);
-- Generate mock data for the orders table
INSERT INTO orders (id, user_id, shop_id, status, created_at, deleted_at)
VALUES
    (1, 1, 1, 'pending', '2023-05-13T00:00:00+07:00', NULL),
    (2, 2, 2, 'delivering', '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the order_detail table
INSERT INTO order_detail (id, order_id, product_id, product_description, quantity, total_price, created_at, deleted_at)
VALUES
    (1, 1, 1,'Color: Red', 2, 39.98, '2023-05-13T00:00:00+07:00', NULL),
    (2, 2, 2,'Color: Red', 1, 499.99, '2023-05-13T00:00:00+07:00', NULL);
GO
-- Generate mock data for the feedback table
INSERT INTO feedback (id, order_detail_id, content, rating, created_at, deleted_at)
VALUES
    (1, 1, 'Great product!', 5, '2023-05-13T00:00:00+07:00', NULL),
    (2, 2, 'Excellent service!', 4, '2023-05-13T00:00:00+07:00', NULL);
GO