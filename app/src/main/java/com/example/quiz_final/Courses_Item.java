package com.example.quiz_final;

public class Courses_Item {
        private String categoryId, categoryName, categoryImage,categoryBackground;

    public String getCategoryBackground() {
        return categoryBackground;
    }

    public void setCategoryBackground(String categoryBackground) {
        this.categoryBackground = categoryBackground;
    }

    public Courses_Item(String categoryId, String categoryName, String categoryImage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public Courses_Item() {}

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
