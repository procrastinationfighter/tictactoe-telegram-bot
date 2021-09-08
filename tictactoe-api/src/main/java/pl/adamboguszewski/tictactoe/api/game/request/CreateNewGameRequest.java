package pl.adamboguszewski.tictactoe.api.game.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.adamboguszewski.tictactoe.api.game.PlayerApiEntity;

import javax.validation.constraints.NotNull;

public class CreateNewGameRequest {

    @JsonProperty("xPlayer")
    @NotNull
    private final PlayerApiEntity xPlayer;

    @JsonProperty("oPlayer")
    @NotNull
    private final PlayerApiEntity oPlayer;

    @NotNull
    private final Long chatId;

    public CreateNewGameRequest(PlayerApiEntity xPlayer, PlayerApiEntity oPlayer, Long chatId) {
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        this.chatId = chatId;
    }

    public PlayerApiEntity getXPlayer() {
        return xPlayer;
    }

    public PlayerApiEntity getOPlayer() {
        return oPlayer;
    }

    public Long getChatId() {
        return chatId;
    }
}
