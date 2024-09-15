package com.andres.footballpg_hibernate.repository;

import com.andres.footballpg_hibernate.entity.TeamEntity;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<TeamEntity, Integer> {

}
