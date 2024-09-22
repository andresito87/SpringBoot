package com.andres.footballpg_hibernate.service;

import com.andres.footballpg_hibernate.dto.TeamPlayers;
import com.andres.footballpg_hibernate.entity.Player;
import com.andres.footballpg_hibernate.entity.PlayerEntity;
import com.andres.footballpg_hibernate.entity.Team;
import com.andres.footballpg_hibernate.entity.TeamEntity;
import com.andres.footballpg_hibernate.repository.AlbumRepository;
import com.andres.footballpg_hibernate.repository.MatchRepository;
import com.andres.footballpg_hibernate.repository.PlayerRepository;
import com.andres.footballpg_hibernate.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FootballService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final AlbumRepository albumRepository;

    public FootballService(
            PlayerRepository playerRepository, 
            TeamRepository teamRepository,
            MatchRepository matchRepository,
            AlbumRepository albumRepository
    ) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.albumRepository = albumRepository;
    }


    // @Transactional(readOnly = true) // It is necessary only when we need to read data from several tables, 
    // the session keeps open until the method ends, so we can read data from different tables.
    // When the repository create a JPQL query, it isn't necessary to use the @Transactional annotation.
    // We recover data in the same transaction with the same sentence.
    public Team getTeam(Integer id) {
        TeamEntity team = teamRepository.findById(id).orElse(null);
        if (team == null) {
            return null;
        } else {
            return new Team(team.getId(),
                    team.getName(),
                    team.getPlayers()
                            .stream()
                            .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                                    player.getDateOfBirth()))
                            .toList());
        }
    }

    public List<Player> searchPlayers(String name) {
        return playerRepository.findByNameContaining(name)
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> searchPlayersStartingWith(String nameStarting) {
        return playerRepository.findByNameStartingWith(nameStarting)
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> searchPlayersByBirthDate(LocalDate date) {
        return playerRepository.findByDateOfBirth(date)
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public Team createTeam(String name) {
        TeamEntity team = new TeamEntity();
        team.setName(name);
        team = teamRepository.save(team);
        return new Team(team.getId(), team.getName(), List.of());
    }

    public Player updatePlayerPosition(Integer id, String position) {
        PlayerEntity player = playerRepository.findById(id).orElse(null);
        if (player == null) {
            return null;
        } else {
            player.setPosition(position);
            player = playerRepository.save(player);
            return new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                    player.getDateOfBirth());
        }
    }

    public List<Player> getPlayersByMatch(Integer id) {
        return matchRepository.findPlayersByMatchId(id)
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> getAlbumMissingPlayers(Integer id) {
        return albumRepository.findByIdMissingPlayers(id)
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> getAlbumPlayers(Integer id) {
        return albumRepository.findByIdPlayers(id, Pageable.ofSize(10).withPage(0))
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> getAlbumPlayersByTeam(Integer albumId, Integer teamId) {
        return albumRepository.findByIdAndTeam(albumId, teamId)
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> getPlayersList(List<Integer> players) {
        // return playerRepository.findListOfPlayers(players)
        return playerRepository.findByIdIn(players)
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> searchPlayersLike(String q) {
        return playerRepository.findByNameLike("%" + q + "%")
                .stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> getTeamPlayers(Integer id) {
        return playerRepository.findByTeamId(id, Sort.by("name").ascending()).stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<Player> getAllPlayersPaged(int pageNumber, int size) {
        Page<PlayerEntity> page = playerRepository.findAll(Pageable.ofSize(size).withPage(pageNumber));
        return page.stream()
                .map(player -> new Player(player.getName(), player.getJerseyNumber(), player.getPosition(),
                        player.getDateOfBirth()))
                .toList();
    }

    public List<TeamPlayers> getNumberOfPlayersByPosition(String position) {
        return teamRepository.getNumberOfPlayersByPosition(position);
    }
}
