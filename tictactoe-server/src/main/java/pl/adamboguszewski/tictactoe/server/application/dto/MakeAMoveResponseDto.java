package pl.adamboguszewski.tictactoe.server.application.dto;

import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.Tile;
import pl.adamboguszewski.tictactoe.server.application.Player;

import java.util.List;

public class MakeAMoveResponseDto {

    private final Long chatId;
    private final List<Tile> boardState;
    private final GameStatus status;
    private final Player xPlayer;
    private final Player oPlayer;
    private final boolean isXNext;

    public MakeAMoveResponseDto(Long chatId,
                                List<Tile> boardState,
                                GameStatus status,
                                Player xPlayer,
                                Player oPlayer,
                                boolean isXNext) {
        this.chatId = chatId;
        this.boardState = boardState;
        this.status = status;
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        this.isXNext = isXNext;
    }

    public Long getChatId() {
        return chatId;
    }

    public List<Tile> getBoardState() {
        return boardState;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getxPlayer() {
        return xPlayer;
    }

    public Player getoPlayer() {
        return oPlayer;
    }

    public boolean isXNext() {
        return isXNext;
    }
}
