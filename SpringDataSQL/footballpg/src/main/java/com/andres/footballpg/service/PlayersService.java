package com.andres.footballpg.service;

import com.andres.footballpg.entities.Player;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayersService {
    private final JdbcClient jdbcClient;
    public PlayersService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Get all players
     * @return List of players
     */
    public List<Player> getPlayers() {
        return jdbcClient.sql("SELECT * FROM players")
                .query(Player.class)
                .list();
    }

    /**
     * Get a player by id
     * @param id Player id
     * @return Player
     */
    public Player getPlayer(int id) {
        return jdbcClient.sql("SELECT * FROM players WHERE id = :id")
                .param("id", id)
                .query(Player.class)
                .single();
    }

    /**
     * Create a player
     * @param player Player
     * @return Player
     */
    public Player createPlayer(Player player) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
     INSERT INTO players (jersey_number, name, position, date_of_birth, team_id)
     VALUES (:jersey_number, :name, :position, :date_of_birth, :team_id)
                  """)
                .param("name", player.getName())
                .param("jersey_number", player.getJerseyNumber())
                .param("position", player.getPosition())
                .param("date_of_birth", player.getDateOfBirth())
                .param("team_id", player.getTeamId())
                .update(keyHolder, "id");
        player.setId(keyHolder.getKey().intValue());
        return player;
    }
}
