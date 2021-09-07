package pl.adamboguszewski.tictactoe.api.game.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.adamboguszewski.tictactoe.api.game.PlayerRequest;

import javax.validation.constraints.NotNull;

public class CreateNewGameRequest {

    @JsonProperty("xPlayer")
    @NotNull
    private final PlayerRequest xPlayer;

    @JsonProperty("oPlayer")
    @NotNull
    private final PlayerRequest oPlayer;

    @NotNull
    private final Long chatId;

    public CreateNewGameRequest(PlayerRequest xPlayer, PlayerRequest oPlayer, Long chatId) {
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        this.chatId = chatId;
    }

    public PlayerRequest getXPlayer() {
        return xPlayer;
    }

    public PlayerRequest getOPlayer() {
        return oPlayer;
    }

    public Long getChatId() {
        return chatId;
    }
}
