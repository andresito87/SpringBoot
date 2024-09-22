package com.andres.footballpg_hibernate.repository;

import com.andres.footballpg_hibernate.entity.PlayerEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    List<PlayerEntity> findByDateOfBirth(LocalDate dateOfBirth);

    List<PlayerEntity> findByNameContaining(String name);

    @Query("SELECT p FROM PlayerEntity p WHERE p.id IN (?1)")
    List<PlayerEntity> findListOfPlayers(List<Integer> players);

    List<PlayerEntity> findByIdIn(List<Integer> players);

    List<PlayerEntity> findByTeamId(Integer teamId, Sort sort);

    List<PlayerEntity> findByNameStartingWith(String name);

    List<PlayerEntity> findByNameLike(String name);
}
