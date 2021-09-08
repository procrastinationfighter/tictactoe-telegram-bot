package pl.adamboguszewski.tictactoe.bot;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import pl.adamboguszewski.tictactoe.api.game.response.CreateNewGameFailureResponse;
import pl.adamboguszewski.tictactoe.api.game.response.CreateNewGameSuccessResponse;

@Component
public class TicTacToeRouteBuilder extends RouteBuilder {

    private static final String createNewGameCommand = "'/newgame'";
    private static final String moveCommand = "'/move'";
    private static final String currentCommand = "'/current'";

    @Override
    public void configure() throws Exception {
        String botCommandText = "${body.text.substring(${body.entities[0].offset}, ${body.entities[0].length()})}";
        Predicate isCommand = simple("${body?.entities.size} > 0 && ${body?.entities[0].type} == 'bot_command'");

        // Default route: check command type and redirect it to its proper route.
        from("telegram:bots")
                .choice()
                        .when(isCommand)
                            .choice()
                                .when(simple(botCommandText + " == " + createNewGameCommand)).to("direct:newgame")
                                .when(simple(botCommandText + " == " + moveCommand)).to("direct:move")
                                .when(simple(botCommandText + " == " + currentCommand)).to("direct:current")
                                .otherwise().log("Not recognized command: " + botCommandText).to("stub:nowhere")
                        .otherwise().process(exchange -> {log.debug("Message not a command.");}).to("stub:nowhere");

        setNewGameRoutes();

        from("direct:move")
                .log("Move")
                .bean(SimpleBot.class)
                .to("telegram:bots");

        from("direct:current")
                .log("Current")
                .bean(SimpleBot.class)
                .to("telegram:bots");
    }

    private void setNewGameRoutes() {
        // Routes for the new game command.

        // Basic route: check if command correct.
        from("direct:newgame")
                .log("New game")
                .bean(CreateNewGameBean.class)
                .log(simple("${body.xPlayer}").getExpressionText())
                .choice()
                    .when(simple("${body.xPlayer} != null")).to("direct:newgamesend")
                    .otherwise().bean(DefaultBean.class).to("telegram:bots");

        // The command was correct, send it to the API.
        from("direct:newgamesend")
                .marshal().json(JsonLibrary.Jackson)
                .log("${body}")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8080/tictactoe/api?throwExceptionOnFailure=false")
                .process(exchange -> {log.info("The response code is: {}", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));})
                .choice()   // [todo] unmarshalling does not work
                    .when(simple("${header.CamelHttpResponseCode} == 200")).to("direct:newgamesuccess")
                    .otherwise().to("direct:newgamefailure");

        // The API told us that the creation was successful.
        from("direct:newgamesuccess")
                .unmarshal(new JacksonDataFormat(CreateNewGameSuccessResponse.class))
                .bean(CreateNewGameBean.class).to("telegram:bots");

        // The API told us that the creation was not successful.
        from("direct:newgamefailure")
                .unmarshal(new JacksonDataFormat(CreateNewGameFailureResponse.class))
                .bean(CreateNewGameBean.class).to("telegram:bots");
    }
}
