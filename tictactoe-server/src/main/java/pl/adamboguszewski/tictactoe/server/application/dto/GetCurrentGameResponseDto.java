package pl.adamboguszewski.tictactoe.server.application.dto;

import pl.adamboguszewski.tictactoe.api.game.Tile;

import java.util.List;

public class GetCurrentGameResponseDto {
    private final Long xPlayerId;
    private final Long oPlayerId;

    private final boolean isXNext;

    private final List<Tile> boardState;

    public GetCurrentGameResponseDto(Long xPlayerId, Long oPlayerId, boolean isXNext, List<Tile> boardState) {
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
