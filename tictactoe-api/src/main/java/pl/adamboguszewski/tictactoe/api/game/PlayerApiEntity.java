package pl.adamboguszewski.tictactoe.api.game;

public class PlayerApiEntity {

    private Long id;
    private String name;

    public PlayerApiEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlayerApiEntity() {
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
}
