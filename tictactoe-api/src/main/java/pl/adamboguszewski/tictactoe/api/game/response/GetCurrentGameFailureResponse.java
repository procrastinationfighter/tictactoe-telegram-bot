package pl.adamboguszewski.tictactoe.api.game.response;

public class GetCurrentGameFailureResponse implements GetCurrentGameResponse {

    private final String message;
    private final Long errorCode;

    public GetCurrentGameFailureResponse(String message, Long errorCode) {
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
