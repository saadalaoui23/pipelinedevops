package com.trajets.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lignes")
public class Ligne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 255)
    private String description;

    // --- Constructeurs ---
    public Ligne() {}

    public Ligne(String code, String nom, String description) {
        this.code = code;
        this.nom = nom;
        this.description = description;
    }

    // --- Getters / Setters ---
    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
