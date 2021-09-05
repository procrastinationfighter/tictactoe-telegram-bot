package pl.adamboguszewski.tictactoe.api.game.request;

import javax.validation.constraints.NotNull;

public class GetCurrentGameRequest {

    @NotNull
    private final Long firstPlayerId;
    @NotNull
    private final Long secondPlayerId;

    public GetCurrentGameRequest(Long firstPlayerId, Long secondPlayerId) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
    }

    public Long getFirstPlayerId() {
        return firstPlayerId;
    }

    public Long getSecondPlayerId() {
        return secondPlayerId;
    }
}
