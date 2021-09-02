package pl.adamboguszewski.tictactoe.api.game.response;

import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.Tile;

import java.util.List;

public class MakeAMoveSuccessResponse implements MakeAMoveResponse {
    private final List<Tile> boardState;
    private final GameStatus status;
    private final boolean isXNext;

    public MakeAMoveSuccessResponse(List<Tile> boardState, GameStatus status, boolean isXNext) {
        this.boardState = boardState;
        this.status = status;
        this.isXNext = isXNext;
    }

    public List<Tile> getBoardState() {
        return boardState;
    }

    public GameStatus getStatus() {
        return status;
    }

    public boolean isXNext() {
        return isXNext;
    }

    public boolean isGameActive() {
        return status.equals(GameStatus.GameActive);
    }
}
