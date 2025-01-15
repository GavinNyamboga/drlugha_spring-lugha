package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long articleId;

    private String name;

    private String avatar;

    @Column(columnDefinition = "TEXT")
    private String text;

    // Getters and setters
}

