package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.User;
import org.springframework.stereotype.Component;
import pl.adamboguszewski.tictactoe.api.game.PlayerRequest;
import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;

@Component
public class CreateNewGameBean {

    public CreateNewGameRequest process(IncomingMessage message) {
        // If the incoming message is not correct, return null as players.
        if (isMessageCorrect(message)) {
            PlayerRequest xPlayer = getPlayerRequestFromUser(message.getFrom());
            PlayerRequest yPlayer = getPlayerRequestFromUser(message.getEntities().get(1).getUser());

            System.out.println("Tworzymy nowego");

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
}
