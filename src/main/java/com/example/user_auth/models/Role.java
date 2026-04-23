package com.example.user_auth.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long Id;

    @Column(nullable = false)
    private String role;

    public Role() {}

    public Role(String role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return Id;
    }

    public enum Values {
        ADMIN("ADMIN"),
        BASIC("BASIC");

        public final String role;

        Values(String role) {
            this.role = role;
        }
    }
}