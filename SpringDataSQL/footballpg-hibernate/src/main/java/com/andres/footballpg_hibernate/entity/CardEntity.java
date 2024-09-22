package com.andres.footballpg_hibernate.entity;

import jakarta.persistence.*;

@Table(name = "cards")
@Entity
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;
}
