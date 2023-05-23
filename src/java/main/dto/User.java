/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dto;

import java.sql.Date;

/**
 *
 * @author ohmin
 */
public class User {

    private int id;
    private String avatar;
    private String email;
    private String password;
    private String fullname;
    private String username;
    private String address;
    private String phone;
    private Date createdAt;
    private Date deletedAt;

    public User() {
    }

    public User(int id, String avatar, String email, String password, String fullname, String username, String address, String phone, Date createdAt, Date deletedAt) {
        this.id = id;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
