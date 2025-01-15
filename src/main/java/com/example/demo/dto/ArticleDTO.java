package com.example.demo.dto;

public class ArticleDTO {
    private String articleName;
    private String articleTitle;
    private String articleCategory;
    private String articleSubCategory;
    private String description;
    private String imageUrl; // Updated to store the image URL
    private Long userId; // Updated to store the user ID

    private Long id;

    // Getters and setters
    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory;
    }

    public String getArticleSubCategory() {
        return articleSubCategory;
    }

    public void setArticleSubCategory(String articleSubCategory) {
        this.articleSubCategory = articleSubCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
