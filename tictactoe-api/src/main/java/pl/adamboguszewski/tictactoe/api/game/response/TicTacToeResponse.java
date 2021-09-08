package pl.adamboguszewski.tictactoe.api.game.response;

public abstract class TicTacToeResponse {
    private Long chatId;

    public TicTacToeResponse(Long chatId) {
        this.chatId = chatId;
    }

    public TicTacToeResponse() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
