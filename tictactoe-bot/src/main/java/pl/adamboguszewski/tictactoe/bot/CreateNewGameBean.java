package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.OutgoingMessage;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.apache.camel.component.telegram.model.User;
import org.springframework.stereotype.Component;
import pl.adamboguszewski.tictactoe.api.game.PlayerRequest;
import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.response.CreateNewGameFailureResponse;
import pl.adamboguszewski.tictactoe.api.game.response.CreateNewGameSuccessResponse;

@Component
public class CreateNewGameBean {

    // Process message from telegram to API.
    public CreateNewGameRequest process(IncomingMessage message) {
        // If the incoming message is not correct, return null as players.
        if (isMessageCorrect(message)) {
            PlayerRequest xPlayer = getPlayerRequestFromUser(message.getFrom());
            PlayerRequest yPlayer = getPlayerRequestFromUser(message.getEntities().get(1).getUser());

            return new CreateNewGameRequest(xPlayer, yPlayer, Long.parseLong(message.getChat().getId()));
        } else {
            return new CreateNewGameRequest(null, null, Long.parseLong(message.getChat().getId()));
        }
    }

    private boolean isMessageCorrect(IncomingMessage message) {
        return message.getEntities().size() > 1 && message.getEntities().get(1).getType().equals("text_mention");
    }

    private PlayerRequest getPlayerRequestFromUser(User user) {
        String name = user.getUsername() == null ? user.getFirstName() + " " + user.getLastName() : user.getUsername();
        return new PlayerRequest(user.getId(), name);
    }

    // Process success message from API to telegram.
    public OutgoingMessage process(CreateNewGameSuccessResponse response) {
        OutgoingTextMessage out = new OutgoingTextMessage();

        String text = "Game created successfully.\n" +
                "Game id: " + response.getGameId();

        out.setChatId(response.getChatId().toString());
        out.setText(text);

        return out;
    }

    // Process failure message from API to telegram.
    public OutgoingMessage process(CreateNewGameFailureResponse response) {
        OutgoingTextMessage out = new OutgoingTextMessage();
        out.setChatId(response.getChatId().toString());
        out.setText("Error " + response.getErrorCode() + " : " + response.getMessage());

        return out;
    }
}
