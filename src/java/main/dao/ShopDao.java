/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dao;

import main.dto.Shop;
import main.utils.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopDao {

    public static ArrayList<Shop> getAllShops() {
        ArrayList<Shop> shopList = new ArrayList<>();

        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "SELECT id, name, address, user_id, created_at, deleted_at FROM shops";
                try ( Statement st = cn.createStatement();  ResultSet rs = st.executeQuery(sqlQuery)) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String address = rs.getString("address");
                        String userId = rs.getString("user_id");
                        Date createdAt = rs.getDate("created_at");
                        Date deletedAt = rs.getDate("deleted_at");
                        Shop shop = new Shop(id, name, address, userId, createdAt, deletedAt);
                        shopList.add(shop);
                    }

                }
            }

            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShopDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return shopList;
    }

    public static boolean addShop(Shop shop) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "INSERT INTO shops (name, address, user_id, created_at, deleted_at) VALUES (?, ?, ?, ?, ?)";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, shop.getName());
                    ps.setString(2, shop.getAddress());
                    ps.setString(3, shop.getUserId());
                    ps.setDate(4, shop.getCreatedAt());
                    ps.setDate(5, shop.getDeletedAt());
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShopDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean updateShop(Shop shop) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "UPDATE shops SET name=?, address=?, user_id=? ,created_at=?, deleted_at=? WHERE id=?";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, shop.getName());
                    ps.setString(2, shop.getAddress());
                    ps.setString(3, shop.getUserId());
                    ps.setDate(4, shop.getCreatedAt());
                    ps.setDate(5, shop.getDeletedAt());
                    ps.setInt(6, shop.getId());
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShopDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean deleteShop(int shopId) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "DELETE FROM shops WHERE id=?";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setInt(1, shopId);
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShopDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
