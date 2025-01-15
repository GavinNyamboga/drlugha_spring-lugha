package drlugha.user_app.entity;

import drlugha.user_app.dto.UserRole;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // Getters and setters

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
