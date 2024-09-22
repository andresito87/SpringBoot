package com.andres.footballpg_hibernate.repository;

import com.andres.footballpg_hibernate.entity.AlbumEntity;
import com.andres.footballpg_hibernate.entity.PlayerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Integer> {

    @Query("SELECT p FROM PlayerEntity p WHERE p NOT IN (SELECT c.player FROM CardEntity c WHERE c.album.id=:id)")
    public List<PlayerEntity> findByIdMissingPlayers(Integer id);

    @Query("SELECT p FROM PlayerEntity p JOIN p.cards c WHERE c.album.id = :id")
    public List<PlayerEntity> findByIdPlayers(Integer id, Pageable page);

    @Query("SELECT p FROM PlayerEntity p JOIN p.cards c WHERE c.album.id = :id AND p.team.id = :teamId")
    public List<PlayerEntity> findByIdAndTeam(Integer id, Integer teamId);
}
