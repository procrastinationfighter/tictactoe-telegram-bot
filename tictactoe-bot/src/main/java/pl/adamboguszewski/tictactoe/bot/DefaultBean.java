package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.component.telegram.model.OutgoingMessage;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.springframework.stereotype.Component;
import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;

@Component
public class DefaultBean {

    OutgoingMessage process(CreateNewGameRequest request) {
        // The message is incorrect.
        OutgoingTextMessage mess = new OutgoingTextMessage();
        mess.setText("Wrong newgame message format. Correct format: \n/newgame @User");
        mess.setChatId(request.getChatId().toString());
        return mess;
    }

    OutgoingMessage process(MakeAMoveRequest request) {
        OutgoingTextMessage mess = new OutgoingTextMessage();
        mess.setText("Wrong move message format. Correct format: \n/move @User <tile_number>\nWhere <tile_number> is from 0 to 8");
        mess.setChatId(request.getChatId().toString());
        return mess;
    }
}
