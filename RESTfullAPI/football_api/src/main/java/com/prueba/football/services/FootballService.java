package com.prueba.football.services;

import com.prueba.football.exceptions.AlreadyExistsException;
import com.prueba.football.exceptions.NotFoundException;
import com.prueba.football.model.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FootballService {
    private final Map<String, Player> players = new HashMap<>(Map.ofEntries(
            Map.entry("1884823", new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 7, 13))),
            Map.entry("325636", new Player("325636", 11, "Alexia PUTELLAS", "Midfielder", LocalDate.of(1994, 2, 4)))
    ));

    public List<Player> listPlayers() {
        return new ArrayList<>(players.values());
    }

    public Player getPlayer(String id) {
        Player player = players.get(id);
        if (player == null)
            throw new NotFoundException("Player not found");
        return player;
    }

    public Player addPlayer(Player player) {
        if (players.containsKey(player.id())) {
            throw new AlreadyExistsException("The player already exists");
        }
        return players.put(player.id(), player);
    }

    public Player updatePlayer(Player player) {
        if (!players.containsKey(player.id())) {
            throw new NotFoundException("The player does not exist");
        }
        return players.put(player.id(), player);

    }

    public Player deletePlayer(String id) {
        return players.remove(id);
    }

}
