
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dao;

import main.dto.Product;
import main.utils.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.dto.Color;
import main.dto.Size;

public class ProductDao {

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();

        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "SELECT id, name, thumbnail_url, description, price, percent_discount, quantity, "
                        + "category_id, total_rating, created_at, deleted_at FROM products";
                try ( Statement st = cn.createStatement();  ResultSet rs = st.executeQuery(sqlQuery)) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String thumbnailUrl = rs.getString("thumbnail_url");
                        String description = rs.getString("description");
                        float price = rs.getFloat("price");
                        float percentDiscount = rs.getFloat("percent_discount");
                        int quantity = rs.getInt("quantity");
                        int categoryId = rs.getInt("category_id");
                        float totalRating = rs.getFloat("total_rating");
                        Date createdAt = rs.getDate("created_at");
                        Date deletedAt = rs.getDate("deleted_at");

                        Product product = new Product(id, name, thumbnailUrl, description, price, percentDiscount,
                                quantity, categoryId, totalRating, createdAt, deletedAt);
                        productList.add(product);
                    }

                }
            }

            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productList;
    }

    public static ArrayList<Product> filterProducts(String productName, Float minPrice, Float maxPrice, Integer categoryId, Integer colorId, Float rating, int page, int size) {
        ArrayList<Product> filteredList = new ArrayList<>();
        int offset = size * (page - 1);
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT p.id, p.name, p.thumbnail_url, p.description, p.price, p.percent_discount, p.quantity, ")
                        .append(" p.category_id, p.total_rating, p.created_at, p.deleted_at FROM products p ");

                if (colorId != null) {
                    sqlQuery.append("JOIN product_color pc ON p.id = pc.product_id ");
                }
                sqlQuery.append("WHERE 1=1");
                // Add filters based on input parameters
                if (productName != null && !productName.isEmpty()) {
                    sqlQuery.append(" AND p.name LIKE '%").append(productName).append("%'");
                }
                if (minPrice != null) {
                    sqlQuery.append(" AND p.price >= ").append(minPrice);
                }
                if (maxPrice != null) {
                    sqlQuery.append(" AND p.price <= ").append(maxPrice);
                }
                if (categoryId != null) {
                    sqlQuery.append(" AND p.category_id = ").append(categoryId);
                }
                if (colorId != null) {
                    sqlQuery.append(" AND pc.color_id = ").append(colorId);
                }
                if (rating != null) {
                    sqlQuery.append(" AND pc.total_rating = ").append(rating);
                }
                sqlQuery.append("ORDER BY id OFFSET ").append(offset).append(" ROWS \nFETCH NEXT ").append(size).append(" ROWS ONLY");
                try ( Statement st = cn.createStatement();  ResultSet rs = st.executeQuery(sqlQuery.toString())) {
                    while (rs.next()) {
                        // Retrieve product details from result set and create Product objects
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String thumbnailUrl = rs.getString("thumbnail_url");
                        String description = rs.getString("description");
                        float price = rs.getFloat("price");
                        float percentDiscount = rs.getFloat("percent_discount");
                        int quantity = rs.getInt("quantity");

                        int fetchedCategoryId = rs.getInt("category_id");
                        float totalRating = rs.getFloat("total_rating");
                        Date createdAt = rs.getDate("created_at");
                        Date deletedAt = rs.getDate("deleted_at");

                        Product product = new Product(id, name, thumbnailUrl, description, price, percentDiscount,
                                quantity, fetchedCategoryId, totalRating, createdAt, deletedAt);
                        filteredList.add(product);
                    }
                }
            }

            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filteredList;
    }

    public static int countFilterProducts(String productName, Float minPrice, Float maxPrice, Integer categoryId, Integer colorId, Float rating) {

        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT p.id FROM products p ");

                if (colorId != null) {
                    sqlQuery.append("JOIN product_color pc ON p.id = pc.product_id ");
                }
                sqlQuery.append("WHERE 1=1");
                // Add filters based on input parameters
                if (productName != null && !productName.isEmpty()) {
                    sqlQuery.append(" AND p.name LIKE '%").append(productName).append("%'");
                }
                if (minPrice != null) {
                    sqlQuery.append(" AND p.price >= ").append(minPrice);
                }
                if (maxPrice != null) {
                    sqlQuery.append(" AND p.price <= ").append(maxPrice);
                }
                if (categoryId != null) {
                    sqlQuery.append(" AND p.category_id = ").append(categoryId);
                }
                if (colorId != null) {
                    sqlQuery.append(" AND pc.color_id = ").append(colorId);
                }
                if (rating != null) {
                    sqlQuery.append(" AND pc.total_rating = ").append(rating);
                }
                String query = "SELECT COUNT(*) as count FROM (" + sqlQuery.toString() + ") AS subquery;";
                try ( Statement st = cn.createStatement();  ResultSet rs = st.executeQuery(query)) {
                    while (rs.next()) {
                        int count = rs.getInt("count");
                        return count;
                    }

                }
            }

            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static Product getProductById(int id) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "SELECT id, name, thumbnail_url, description, price, percent_discount, quantity,category_id, total_rating, created_at, deleted_at FROM products WHERE id = ?";
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setInt(1, id);
                    try ( ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String name = rs.getString("name");
                            String thumbnailUrl = rs.getString("thumbnail_url");
                            String description = rs.getString("description");
                            float price = rs.getFloat("price");
                            float percentDiscount = rs.getFloat("percent_discount");
                            int quantity = rs.getInt("quantity");
                            int categoryId = rs.getInt("category_id");
                            float totalRating = rs.getFloat("total_rating");
                            Date createdAt = rs.getDate("created_at");
                            Date deletedAt = rs.getDate("deleted_at");

                            Product product = new Product(id, name, thumbnailUrl, description, price, percentDiscount,
                                    quantity, categoryId, totalRating, createdAt, deletedAt);
                            ArrayList<Color> colors = ProductColorDao.getAllProductColorByProductId(id);
                            product.setColors(colors);
                            ArrayList<Size> sizes = ProductSizeDao.getAllProductSizeByProductId(id);
                            product.setSizes(sizes);
                            return product;

                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static boolean addProduct(Product product) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "INSERT INTO products (name, thumbnail_url, description, price, percent_discount, "
                        + "quantity, category_id, total_rating, created_at, deleted_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, product.getName());
                    ps.setString(2, product.getThumbnailUrl());
                    ps.setString(3, product.getDescription());
                    ps.setFloat(4, product.getPrice());
                    ps.setFloat(5, product.getPercentDiscount());
                    ps.setInt(6, product.getQuantity());
                    ps.setInt(7, product.getCategoryId());
                    ps.setFloat(8, product.getTotalRating());
                    ps.setDate(9, Date.valueOf(LocalDate.now()));
                    ps.setDate(10, null);
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean updateProduct(Product product) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "UPDATE products SET name=?, thumbnail_url=?, description=?, price=?, "
                        + "percent_discount=?, quantity=?, category_id=?, total_rating=?, created_at=?, deleted_at=? "
                        + "WHERE id=?";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, product.getName());
                    ps.setString(2, product.getThumbnailUrl());
                    ps.setString(3, product.getDescription());
                    ps.setFloat(4, product.getPrice());
                    ps.setFloat(5, product.getPercentDiscount());
                    ps.setInt(6, product.getQuantity());
                    ps.setInt(7, product.getCategoryId());
                    ps.setFloat(8, product.getTotalRating());
                    ps.setDate(9, product.getCreatedAt());
                    ps.setDate(10, product.getDeletedAt());
                    ps.setInt(11, product.getId());
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean deleteProduct(int productId) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "DELETE FROM products WHERE id=?";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setInt(1, productId);
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
