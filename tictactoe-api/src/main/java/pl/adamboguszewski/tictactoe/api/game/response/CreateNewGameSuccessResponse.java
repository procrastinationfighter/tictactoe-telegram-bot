package pl.adamboguszewski.tictactoe.api.game.response;

public class CreateNewGameSuccessResponse implements CreateNewGameResponse {

    private final Long id;

    public CreateNewGameSuccessResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
