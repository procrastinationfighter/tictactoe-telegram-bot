package pl.adamboguszewski.tictactoe.api.game.response;

public class MakeAMoveFailureResponse extends TicTacToeResponse implements MakeAMoveResponse {

    private String message;
    private Long errorCode;

    public MakeAMoveFailureResponse(Long chatId, String message, Long errorCode) {
        super(chatId);
        this.message = message;
        this.errorCode = errorCode;
    }

    public MakeAMoveFailureResponse() {
    }

    public String getMessage() {
        return message;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }
}
