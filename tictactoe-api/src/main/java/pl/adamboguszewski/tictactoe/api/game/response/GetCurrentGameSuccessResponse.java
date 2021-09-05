package pl.adamboguszewski.tictactoe.api.game.response;

import pl.adamboguszewski.tictactoe.api.game.Tile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GetCurrentGameSuccessResponse implements GetCurrentGameResponse {

    @NotNull
    private final Long xPlayerId;
    @NotNull
    private final Long oPlayerId;

    @NotNull
    private final boolean isXNext;

    @NotNull
    private final List<Tile> boardState;

    public GetCurrentGameSuccessResponse(Long xPlayerId, Long oPlayerId, boolean isXNext, List<Tile> boardState) {
        this.xPlayerId = xPlayerId;
        this.oPlayerId = oPlayerId;
        this.isXNext = isXNext;
        this.boardState = boardState;
    }

    public Long getxPlayerId() {
        return xPlayerId;
    }

    public Long getoPlayerId() {
        return oPlayerId;
    }

    public boolean isXNext() {
        return isXNext;
    }

    public List<Tile> getBoardState() {
        return boardState;
    }
}
