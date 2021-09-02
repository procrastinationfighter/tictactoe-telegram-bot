package pl.adamboguszewski.tictactoe.api.game.request;

import pl.adamboguszewski.tictactoe.api.game.PlayerRequest;

import javax.validation.constraints.NotNull;

public class CreateNewGameRequest {
    @NotNull
    private final PlayerRequest xPlayer;
    @NotNull
    private final PlayerRequest oPlayer;

    @NotNull
    private final Long chatId;

    public CreateNewGameRequest(PlayerRequest xPlayer, PlayerRequest oPlayer, Long chatId) {
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        this.chatId = chatId;
    }

    public PlayerRequest getxPlayer() {
        return xPlayer;
    }

    public PlayerRequest getoPlayer() {
        return oPlayer;
    }

    public Long getChatId() {
        return chatId;
    }
}
