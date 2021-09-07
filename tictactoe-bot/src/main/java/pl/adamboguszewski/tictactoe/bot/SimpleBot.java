package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.OutgoingMessage;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.springframework.stereotype.Component;

@Component
public class SimpleBot {

    public OutgoingMessage process(IncomingMessage message) {
        OutgoingTextMessage mess = new OutgoingTextMessage();
        StringBuilder text = new StringBuilder();

        mess.setChatId(message.getChat().getId());
        text.append("From chat ")
                .append(message.getChat().getTitle())
                .append(", message: ")
                .append(message.getText());
        System.out.println(message.getText());

        for (var ent : message.getEntities()) {
            System.out.println(ent.getType());
            if (ent.getType().equals("text_mention")) {
                System.out.println("mention of user " + ent.getUser());
                text.append(" [wolany user](tg://user?id=").append(ent.getUser().getId()).append(")");
            }
        }
        mess.setText(text.toString());
        mess.setParseMode("Markdown");
        return mess;
    }
}
