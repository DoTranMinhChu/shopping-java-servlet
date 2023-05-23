/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import java.sql.Date;

/**
 *
 * @author dotra
 */
public class Product {

    private int id;
    private String name;
    private String thumbnailUrl;
    private String description;
    private float price;
    private float percentDiscount;
    private int quantity;
    private int shopId;
    private int categoryId;
    private float totalRating;
    private Date createdAt;
    private Date deletedAt;

    public Product() {
    }

    public Product(int id, String name, String thumbnailUrl, String description, float price, float percentDiscount, int quantity, int shopId, int categoryId, float totalRating, Date createdAt, Date deletedAt) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.price = price;
        this.percentDiscount = percentDiscount;
        this.quantity = quantity;
        this.shopId = shopId;
        this.categoryId = categoryId;
        this.totalRating = totalRating;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(float percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(float totalRating) {
        this.totalRating = totalRating;
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
