package pl.adamboguszewski.tictactoe.api.game.response;

public class CreateNewGameSuccessResponse extends TicTacToeResponse implements CreateNewGameResponse {

    private Long gameId;

    public CreateNewGameSuccessResponse(Long chatId, Long gameId) {
        super(chatId);
        this.gameId = gameId;
    }

    public CreateNewGameSuccessResponse() {
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
