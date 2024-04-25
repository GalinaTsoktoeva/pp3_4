package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "roles")
    private String name;

//    @Override
//    public String getAuthority() {
//        return name;
//    }
//    public Role(){};
//    public Role(String name) {
//        this.name = name;
//    }

//    @Override
//    public String toString() {
//        return name;
//    }
}
