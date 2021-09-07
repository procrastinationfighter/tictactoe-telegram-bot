package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.component.telegram.model.OutgoingMessage;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.springframework.stereotype.Component;
import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;

@Component
public class DefaultBean {

    OutgoingMessage process(CreateNewGameRequest request) {
        // The message is incorrect.
        OutgoingTextMessage mess = new OutgoingTextMessage();
        mess.setText("Wrong message format. Correct format: \n/newgame @User");
        mess.setChatId(request.getChatId().toString());
        return mess;
    }
}
