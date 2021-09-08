package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.OutgoingMessage;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.springframework.stereotype.Component;
import pl.adamboguszewski.tictactoe.api.game.PlayerApiEntity;
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

        builder.append(addPlayerText(response));

        out.setText(builder.toString());
        out.setChatId(response.getChatId().toString());

        out.setParseMode("Markdown");

        return out;
    }

    private String getBoardStateString(List<Tile> boardState) {
        int BOARD_SIDE = 3;
        char VERTICAL_LINE = '│', HORIZONTAL_LINE = '─', INTERSECTION = '┼';
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < BOARD_SIDE; i++) {
            for (int j = 0; j < BOARD_SIDE; j++) {
                var tile = boardState.get(i * BOARD_SIDE + j);
                switch (tile) {
                    case X -> builder.append("X ");
                    case O -> builder.append("O ");
                    case None -> builder.append("    ");
                }

                if (j != BOARD_SIDE - 1) {
                    builder.append(VERTICAL_LINE);
                }
            }
            if (i != BOARD_SIDE - 1) {
                builder.append('\n');
                for (int j = 0; j < BOARD_SIDE; j++) {
                    builder.append(HORIZONTAL_LINE);
                    if (j != BOARD_SIDE - 1) {
                        builder.append(INTERSECTION);
                    }
                }
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    private String addPlayerText(MakeAMoveSuccessResponse response) {
        PlayerApiEntity player;
        StringBuilder builder = new StringBuilder();

        switch (response.getStatus()) {
            case GameActive -> {
                if (response.isXNext()) {
                    player = response.getXPlayer();
                } else {
                    player = response.getOPlayer();
                }
                builder.append("Turn of: ");
            }
            case XWon -> {
                player = response.getXPlayer();
                builder.append("Congratulations to the X player: ");
            }
            case OWon -> {
                player = response.getOPlayer();
                builder.append("Congratulations to the O player: ");
            }
            case Draw -> {
                return "";
            }
            default -> throw new IllegalStateException("Unexpected game status");
        }
        builder.append(getUserMentionString(player));

        return builder.toString();
    }

    private String getUserMentionString(PlayerApiEntity player) {
        return "[" + player.getName() + "](tg://user?id=" + player.getId() + ")";
    }

    public OutgoingMessage process(MakeAMoveFailureResponse response) {
        OutgoingTextMessage out = new OutgoingTextMessage();
        out.setChatId(response.getChatId().toString());
        out.setText("Error " + response.getErrorCode() + " : " + response.getMessage());

        return out;
    }
}
