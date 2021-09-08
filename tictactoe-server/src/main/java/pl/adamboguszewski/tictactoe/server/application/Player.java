package pl.adamboguszewski.tictactoe.server.application;

import pl.adamboguszewski.tictactoe.api.game.PlayerApiEntity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {
    @Id
    private Long id;
    private String name;

    public Player() {
    }

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Player fromPlayerRequest(PlayerApiEntity request) {
        return new Player(request.getId(), request.getName());
    }
}
