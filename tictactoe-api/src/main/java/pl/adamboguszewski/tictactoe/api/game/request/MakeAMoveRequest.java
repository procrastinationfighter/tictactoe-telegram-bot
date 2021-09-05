package pl.adamboguszewski.tictactoe.api.game.request;

import pl.adamboguszewski.tictactoe.api.game.PlayerRequest;

import javax.validation.constraints.NotNull;

public class MakeAMoveRequest {

    @NotNull
    private final Long whoPlayed;
    @NotNull
    private final Long otherPlayer;

    @NotNull
    private final Integer tileNumber;

    public MakeAMoveRequest(Long whoPlayed, Long otherPlayer, Integer tileNumber) {
        this.whoPlayed = whoPlayed;
        this.otherPlayer = otherPlayer;
        this.tileNumber = tileNumber;
    }

    public Long getWhoPlayed() {
        return whoPlayed;
    }

    public Long getOtherPlayer() {
        return otherPlayer;
    }

    public Integer getTileNumber() {
        return tileNumber;
    }
}
