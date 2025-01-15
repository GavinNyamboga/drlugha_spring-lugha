package com.example.demo.entity;

import javax.persistence.*;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String articleName;

    private String articleCategory;

    private String articleSubCategory;

    private String articleTitle;

    @Column(columnDefinition = "TEXT") // Use TEXT data type for 'description' field
    private String description;

    @Column(columnDefinition = "TEXT") // Use TEXT data type for 'description' field
    private String imageUrl; // Updated to store the image URL

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
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

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
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

    // Getters and setters
    // Constructors, etc.
}

