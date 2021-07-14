package com.example.springbootdemo.servletweb.player;

import com.example.springbootdemo.servletweb.exception.ObjectNotFoundException;
import com.example.springbootdemo.servletweb.util.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
class PlayerService {

    private final List<Player> playerHolder =
        Collections.synchronizedList(new ArrayList<>(16));

    Player save(Player player) {
        player.setId(IdGenerator.sequenceId());
        this.playerHolder.add(player);
        return player;
    }

    Player update(Player player) {
        for (int i = 0, len = this.playerHolder.size(); i < len; i++) {
            Player target = this.playerHolder.get(i);
            if (player.getId().equals(target.getId())) {
                this.playerHolder.set(i, player);
                return target;
            }
        }
        throw new ObjectNotFoundException();
    }

    Player delete(String id) {
        return findById(id)
            .map(it -> {
                this.playerHolder.remove(it);
                return it;
            })
            .orElseThrow(ObjectNotFoundException::new);
    }

    Player find(String id) {
        return findById(id)
            .orElseThrow(ObjectNotFoundException::new);
    }

    Collection<Player> findAll() {
        return playerHolder;
    }

    private Optional<Player> findById(String id) {
        if (!StringUtils.hasText(id)) {
            return Optional.empty();
        }
        return playerHolder.stream()
            .filter(it -> it.getId().equals(id))
            .findFirst();
    }

}
