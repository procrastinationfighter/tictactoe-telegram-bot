package pl.adamboguszewski.tictactoe.api.game.response;

import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.Tile;

import java.util.List;

public class MakeAMoveSuccessResponse extends TicTacToeResponse implements MakeAMoveResponse {
    private List<Tile> boardState;
    private GameStatus status;
    private boolean isXNext;

    public MakeAMoveSuccessResponse(Long chatId, List<Tile> boardState, GameStatus status, boolean isXNext) {
        super(chatId);
        this.boardState = boardState;
        this.status = status;
        this.isXNext = isXNext;
    }

    public MakeAMoveSuccessResponse() {
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

    public void setBoardState(List<Tile> boardState) {
        this.boardState = boardState;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setXNext(boolean XNext) {
        isXNext = XNext;
    }
}
