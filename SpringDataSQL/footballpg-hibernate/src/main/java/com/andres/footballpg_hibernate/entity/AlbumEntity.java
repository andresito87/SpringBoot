package com.andres.footballpg_hibernate.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "albums")
@Entity
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private LocalDate expireDate;
    @OneToMany
    private List<CardEntity> cards;
}
