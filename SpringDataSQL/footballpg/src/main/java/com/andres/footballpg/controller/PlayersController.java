package com.andres.footballpg.controller;

import com.andres.footballpg.entities.Player;
import com.andres.footballpg.service.PlayersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/players")
@RestController
public class PlayersController {

    private PlayersService playersService;

    public PlayersController(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping
    public List<Player> getPlayers() {
        return playersService.getPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable int id) {
        return playersService.getPlayer(id);
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playersService.createPlayer(player);
    }


}
