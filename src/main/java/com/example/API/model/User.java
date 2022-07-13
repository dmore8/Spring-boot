package com.example.API.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="nombre")
    private String nombre;
    @Column(name ="password")
    private String password;
    @Column(name ="estado")
    private String estado;

    public User() {
    }

    public User(int id, String nombre, String password, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //other setters and getters
}