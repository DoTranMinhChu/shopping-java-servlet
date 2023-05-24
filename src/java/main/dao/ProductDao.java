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

public class ProductDao {

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();

        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "SELECT id, name, thumbnail_url, description, price, percent_discount, quantity, "
                        + "shop_id, category_id, total_rating, created_at, deleted_at FROM products";
                try ( Statement st = cn.createStatement();  ResultSet rs = st.executeQuery(sqlQuery)) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String thumbnailUrl = rs.getString("thumbnail_url");
                        String description = rs.getString("description");
                        float price = rs.getFloat("price");
                        float percentDiscount = rs.getFloat("percent_discount");
                        int quantity = rs.getInt("quantity");
                        int shopId = rs.getInt("shop_id");
                        int categoryId = rs.getInt("category_id");
                        float totalRating = rs.getFloat("total_rating");
                        Date createdAt = rs.getDate("created_at");
                        Date deletedAt = rs.getDate("deleted_at");

                        Product product = new Product(id, name, thumbnailUrl, description, price, percentDiscount,
                                quantity, shopId, categoryId, totalRating, createdAt, deletedAt);
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

    public static boolean addProduct(Product product) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "INSERT INTO products (name, thumbnail_url, description, price, percent_discount, "
                        + "quantity, shop_id, category_id, total_rating, created_at, deleted_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, product.getName());
                    ps.setString(2, product.getThumbnailUrl());
                    ps.setString(3, product.getDescription());
                    ps.setFloat(4, product.getPrice());
                    ps.setFloat(5, product.getPercentDiscount());
                    ps.setInt(6, product.getQuantity());
                    ps.setInt(7, product.getShopId());
                    ps.setInt(8, product.getCategoryId());
                    ps.setFloat(9, product.getTotalRating());
                    ps.setDate(10, Date.valueOf(LocalDate.now()));
                    ps.setDate(11, null);
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
                        + "percent_discount=?, quantity=?, shop_id=?, category_id=?, total_rating=?, created_at=?, deleted_at=? "
                        + "WHERE id=?";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, product.getName());
                    ps.setString(2, product.getThumbnailUrl());
                    ps.setString(3, product.getDescription());
                    ps.setFloat(4, product.getPrice());
                    ps.setFloat(5, product.getPercentDiscount());
                    ps.setInt(6, product.getQuantity());
                    ps.setInt(7, product.getShopId());
                    ps.setInt(8, product.getCategoryId());
                    ps.setFloat(9, product.getTotalRating());
                    ps.setDate(10, product.getCreatedAt());
                    ps.setDate(11, product.getDeletedAt());
                    ps.setInt(12, product.getId());
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
