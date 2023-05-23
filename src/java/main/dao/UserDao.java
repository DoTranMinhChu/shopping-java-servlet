package main.dao;

import main.dto.User;
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

public class UserDao {

    public static User getInfoUserByEmail(String emailCheck) {
        User user = null;

        try ( Connection cn = DBUtil.makeConnection()) {

            if (cn != null) {
                String sqlQuery = "SELECT id, email,avatar,password,fullname,username,phone,address,created_at,deleted_at \n"
                        + "FROM users \n"
                        + "WHERE status=1 AND email = ? COLLATE Latin1_General_CS_AS";
                PreparedStatement pst = cn.prepareStatement(sqlQuery);
                pst.setString(1, emailCheck);
                ResultSet rs = pst.executeQuery();
                // step 3 :
                if (!rs.next()) {
                    return null;
                } else {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String avatar = rs.getString("avatar");
                    String fullname = rs.getString("fullname");
                    String phone = rs.getString("phone");
                    String username = rs.getString("username");
                    String address = rs.getString("address");
                    Date createdAt = rs.getDate("created_at");
                    Date deletedAt = rs.getDate("deleted_at");
                    user = new User(id, avatar, email, password, fullname, username, address, phone, createdAt, deletedAt);
                }
            }
            cn.close();
        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();

        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "SELECT id, avatar, email, password, fullname, username, address, phone, created_at, deleted_at \n"
                        + "FROM users \n";
                try ( Statement st = cn.createStatement();  ResultSet rs = st.executeQuery(sqlQuery)) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String avatar = rs.getString("avatar");
                        String email = rs.getString("email");
                        String password = rs.getString("password");
                        String fullname = rs.getString("fullname");
                        String username = rs.getString("username");
                        String address = rs.getString("address");
                        String phone = rs.getString("phone");
                        Date createdAt = rs.getDate("created_at");
                        Date deletedAt = rs.getDate("deleted_at");

                        User user = new User(id, avatar, email, password, fullname, username, address, phone, createdAt, deletedAt);
                        userList.add(user);
                    }

                }
            }

            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userList;
    }

    public static boolean addUser(User user) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "INSERT INTO users (avatar, email, password, fullname, username, address, phone, created_at) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, user.getAvatar());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setString(4, user.getFullname());
                    ps.setString(5, user.getUsername());
                    ps.setString(6, user.getAddress());
                    ps.setString(7, user.getPhone());
                    ps.setDate(8, user.getCreatedAt());
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean updateUser(User user) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "UPDATE users SET avatar=?, email=?, password=?, fullname=?, username=?, "
                        + "address=?, phone=?, created_at=?, deleted_at=? WHERE id=?";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setString(1, user.getAvatar());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setString(4, user.getFullname());
                    ps.setString(5, user.getUsername());
                    ps.setString(6, user.getAddress());
                    ps.setString(7, user.getPhone());
                    ps.setDate(8, user.getCreatedAt());
                    ps.setDate(9, user.getDeletedAt());
                    ps.setInt(10, user.getId());
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean deleteUser(int userId) {
        try ( Connection cn = DBUtil.makeConnection()) {
            if (cn != null) {
                String sqlQuery = "DELETE FROM users WHERE id=?";
                int rowsAffected;
                try ( PreparedStatement ps = cn.prepareStatement(sqlQuery)) {
                    ps.setInt(1, userId);
                    rowsAffected = ps.executeUpdate();
                }
                cn.close();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
