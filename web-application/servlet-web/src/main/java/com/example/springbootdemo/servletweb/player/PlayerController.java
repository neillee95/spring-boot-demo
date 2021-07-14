package com.example.springbootdemo.servletweb.player;

import com.example.springbootdemo.servletweb.exception.RequestParamsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public Player save(@Valid @RequestBody Player player) {
        return playerService.save(player);
    }

    @PutMapping
    public Player update(@Valid @RequestBody Player player) {
        if (!StringUtils.hasText(player.getId())) {
            throw new RequestParamsException();
        }
        return playerService.update(player);
    }

    @DeleteMapping("/{id:\\d+}")
    public Player delete(@PathVariable("id") String id) {
        return playerService.delete(id);
    }

    @GetMapping("/{id:\\d+}")
    public Player findById(@PathVariable("id") String id) {
        return playerService.find(id);
    }

    @GetMapping
    public Collection<Player> findAll() {
        return playerService.findAll();
    }

}
