package pl.adamboguszewski.tictactoe.api.game.request;

import javax.validation.constraints.NotNull;

public class MakeAMoveRequest {

    @NotNull
    private final Long chatId;

    @NotNull
    private final Long whoPlayed;
    @NotNull
    private final Long otherPlayer;

    @NotNull
    private final Integer tileNumber;

    public MakeAMoveRequest(Long chatId, Long whoPlayed, Long otherPlayer, Integer tileNumber) {
        this.chatId = chatId;
        this.whoPlayed = whoPlayed;
        this.otherPlayer = otherPlayer;
        this.tileNumber = tileNumber;
    }

    public Long getChatId() {
        return chatId;
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
