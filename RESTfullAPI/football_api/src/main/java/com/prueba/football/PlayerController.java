package com.prueba.football;

import java.util.List;

import com.prueba.football.exceptions.AlreadyExistsException;
import com.prueba.football.exceptions.NotFoundException;
import com.prueba.football.model.Player;
import com.prueba.football.services.FootballService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/players")
@RestController
public class PlayerController {
    private final FootballService footballService;

    public PlayerController(FootballService footballService) {
        this.footballService = footballService;
    }

    @GetMapping
    public List<Player> listPlayers() {
        return footballService.listPlayers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> readPlayer(@PathVariable String id) {
        // Important: Return a ResponseEntity of Player and response status OK
        // or NOT_FOUND if the player is not found
        try {
            Player player = footballService.getPlayer(id);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        try {
            footballService.addPlayer(player);
            return new ResponseEntity<>(player, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public void updatePlayer(@PathVariable String id,
                             @RequestBody Player player) {
        footballService.updatePlayer(player);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable String id) {
        Player player = footballService.deletePlayer(id);
        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    // Commented code because to handle exceptions, it's using a global exception handler
    /*@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")
    @ExceptionHandler(NotFoundException.class)
    public void notFoundHandler() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already exists")
    @ExceptionHandler(AlreadyExistsException.class) public void alreadyExistsHandler() {
    }*/

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<String>
        handleGlobalException(NotFoundException ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(AlreadyExistsException.class)
        public ResponseEntity<String>
        handleGlobalException(AlreadyExistsException ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
