package pl.adamboguszewski.tictactoe.api.game.request;

import pl.adamboguszewski.tictactoe.api.game.PlayerRequest;

import javax.validation.constraints.NotNull;

public class MakeAMoveRequest {
    @NotNull
    private final Long chatId;

    @NotNull
    private final PlayerRequest whoPlayed;
    @NotNull
    private final PlayerRequest otherPlayer;

    @NotNull
    private final Integer tileNumber;

    public MakeAMoveRequest(Long chatId, PlayerRequest whoPlayed, PlayerRequest otherPlayer, Integer tileNumber) {
        this.chatId = chatId;
        this.whoPlayed = whoPlayed;
        this.otherPlayer = otherPlayer;
        this.tileNumber = tileNumber;
    }

    public Long getChatId() {
        return chatId;
    }

    public PlayerRequest getWhoPlayed() {
        return whoPlayed;
    }

    public PlayerRequest getOtherPlayer() {
        return otherPlayer;
    }

    public Integer getTileNumber() {
        return tileNumber;
    }
}
