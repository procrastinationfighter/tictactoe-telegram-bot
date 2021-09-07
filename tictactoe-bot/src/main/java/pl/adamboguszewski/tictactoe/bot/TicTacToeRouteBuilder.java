package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TicTacToeRouteBuilder extends RouteBuilder {

    private static final String createNewGameCommand = "'/newgame'";
    private static final String moveCommand = "'/move'";
    private static final String currentCommand = "'/current'";

    @Override
    public void configure() throws Exception {
        String simpleCommandText = "${body.text.substring(${body.entities[0].offset}, ${body.entities[0].length()})}";
        Predicate isCommand = simple("${body?.entities.size} > 0 && ${body?.entities[0].type} == 'bot_command'");

        // Default route: check command type and redirect it to its proper route.
        from("telegram:bots")
                .choice()
                        .when(isCommand)
                            .choice()
                                .when(simple(simpleCommandText + " == " + createNewGameCommand)).to("direct:newgame")
                                .when(simple(simpleCommandText + " == " + moveCommand)).to("direct:move")
                                .when(simple(simpleCommandText + " == " + currentCommand)).to("direct:current")
                                .otherwise().log("Not recognized command: " + simpleCommandText).to("stub:nowhere")
                        .otherwise().process(exchange -> {log.debug("Message not a command.");}).to("stub:nowhere");

        from("direct:newgame")
                .log("New game")
                .bean(SimpleBot.class)
                .to("telegram:bots");

        from("direct:move")
                .log("Move")
                .bean(SimpleBot.class)
                .to("telegram:bots");

        from("direct:current")
                .log("Current")
                .bean(SimpleBot.class)
                .to("telegram:bots");
    }
}
