package pl.adamboguszewski.tictactoe.server.application.dto;

import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.Tile;

import java.util.List;

public class MakeAMoveResponseDto {

    private final Long chatId;
    private final List<Tile> boardState;
    private final GameStatus status;
    private final boolean isXNext;

    public MakeAMoveResponseDto(Long chatId, List<Tile> boardState, GameStatus status, boolean isXNext) {
        this.chatId = chatId;
        this.boardState = boardState;
        this.status = status;
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

    public boolean isXNext() {
        return isXNext;
    }

    public boolean isGameActive() {
        return status.equals(GameStatus.GameActive);
    }
}
