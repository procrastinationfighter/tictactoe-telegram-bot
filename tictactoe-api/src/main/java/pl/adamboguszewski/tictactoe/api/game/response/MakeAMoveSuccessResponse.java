package pl.adamboguszewski.tictactoe.api.game.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.PlayerApiEntity;
import pl.adamboguszewski.tictactoe.api.game.Tile;

import java.util.List;

public class MakeAMoveSuccessResponse extends TicTacToeResponse implements MakeAMoveResponse {

    private List<Tile> boardState;
    private GameStatus status;
    private boolean isXNext;

    @JsonProperty("xPlayer")
    private PlayerApiEntity xPlayer;
    @JsonProperty("oPlayer")
    private PlayerApiEntity oPlayer;

    public MakeAMoveSuccessResponse(Long chatId,
                                    List<Tile> boardState,
                                    GameStatus status,
                                    PlayerApiEntity xPlayer,
                                    PlayerApiEntity oPlayer,
                                    boolean isXNext) {
        super(chatId);
        this.boardState = boardState;
        this.status = status;
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
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

    public PlayerApiEntity getXPlayer() {
        return xPlayer;
    }

    public PlayerApiEntity getOPlayer() {
        return oPlayer;
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

    public void setXPlayer(PlayerApiEntity xPlayer) {
        this.xPlayer = xPlayer;
    }

    public void setOPlayer(PlayerApiEntity oPlayer) {
        this.oPlayer = oPlayer;
    }

    public void setXNext(boolean XNext) {
        isXNext = XNext;
    }
}
