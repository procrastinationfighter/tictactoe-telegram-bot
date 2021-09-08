package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.OutgoingMessage;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.springframework.stereotype.Component;
import pl.adamboguszewski.tictactoe.api.game.Tile;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;
import pl.adamboguszewski.tictactoe.api.game.response.MakeAMoveFailureResponse;
import pl.adamboguszewski.tictactoe.api.game.response.MakeAMoveSuccessResponse;

import java.util.List;

@Component
public class MoveBean {

    public MakeAMoveRequest process(IncomingMessage message) {
        Long chatId = Long.parseLong(message.getChat().getId());
        try {
            Integer tileNumber = getTileNumber(message);

            return new MakeAMoveRequest(chatId,
                    message.getFrom().getId(),
                    message.getEntities().get(1).getUser().getId(),
                    tileNumber);
        } catch (RuntimeException e) {
            return new MakeAMoveRequest(chatId, null, null, null);
        }
    }

    private Integer getTileNumber(IncomingMessage message) {
        boolean isMention = message.getEntities().size() > 1 && message.getEntities().get(1).getType().equals("text_mention");

        if (isMention) {
            var mention = message.getEntities().get(1);
            return Integer.parseInt(message.getText().substring(mention.getOffset() + mention.getLength()).trim());
        } else {
            throw new RuntimeException();
        }
    }

    public OutgoingMessage process(MakeAMoveSuccessResponse response) {
        OutgoingTextMessage out = new OutgoingTextMessage();
        StringBuilder builder = new StringBuilder();

        builder.append("Game status: ");

        switch (response.getStatus()) {
            case GameActive -> builder.append("Active.\n");
            case XWon -> builder.append("X player has won!\n");
            case OWon -> builder.append("O player has won!\n");
            case Draw -> builder.append("Draw.\n");
        }

        builder.append("Board state after the move:\n");
        builder.append(getBoardStateString(response.getBoardState()));

        out.setText(builder.toString());
        out.setChatId(response.getChatId().toString());

        return out;
    }

    private String getBoardStateString(List<Tile> boardState) {
        // todo do it better
        StringBuilder builder = new StringBuilder();
        for (var tile : boardState) {
            builder.append(tile.toString()).append(" ");
        }

        return builder.toString();
    }

    public OutgoingMessage process(MakeAMoveFailureResponse response) {
        OutgoingTextMessage out = new OutgoingTextMessage();
        out.setChatId(response.getChatId().toString());
        out.setText("Error " + response.getErrorCode() + " : " + response.getMessage());

        return out;
    }
}
