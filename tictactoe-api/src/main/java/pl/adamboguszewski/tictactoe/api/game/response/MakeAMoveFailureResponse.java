package pl.adamboguszewski.tictactoe.api.game.response;

public class MakeAMoveFailureResponse implements MakeAMoveResponse {

    private final String message;
    private final Long errorCode;

    public MakeAMoveFailureResponse(String message, Long errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Long getErrorCode() {
        return errorCode;
    }
}
