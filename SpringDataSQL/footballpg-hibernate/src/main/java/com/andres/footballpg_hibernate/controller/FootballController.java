package com.andres.footballpg_hibernate.controller;

import com.andres.footballpg_hibernate.entity.Player;
import com.andres.footballpg_hibernate.entity.Team;
import com.andres.footballpg_hibernate.service.FootballService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/football")
@RestController
public class FootballController {

    private final FootballService footballService;

    public FootballController(FootballService footballService) {
        this.footballService = footballService;
    }

    /**
     * Get all players
     * @return List<Player>
     * @example /football/teams/1
     */
    @GetMapping("/teams/{id}")
    public Team getTeam(@PathVariable Integer id) {
        return footballService.getTeam(id);
    }

    /**
     * Get all players
     * @param search String
     * @return List<Player>
     * @example /football/players?search=and
     */
    @GetMapping("/players")
    public List<Player> searchPlayers(@RequestParam String search) {
        return footballService.searchPlayers(search);
    }

    /**
     * Get all players
     * @param date LocalDate
     * @return List<Player>
     * @example /football/players/birth/1990-01-01
     */
    @GetMapping("/players/birth/{date}")
    public List<Player> searchPlayersByBirthDate(@PathVariable LocalDate date) {
        return footballService.searchPlayersByBirthDate(date);
    }

    /**
     * Get all players
     * @param name String
     * @return Player
     * @example /football/teams
     */
    @PostMapping("/teams")
    public Team createTeam(@RequestBody String name) {
        return footballService.createTeam(name);
    }

    /**
     * Get all players
     * @param id Integer
     * @return Player
     * @example /football/player/1/position
     */
    @PutMapping("/player/{id}/position")
    public Player updatePlayerPosition(@PathVariable Integer id, @RequestBody String position) {
        return footballService.updatePlayerPosition(id, position);
    }

}