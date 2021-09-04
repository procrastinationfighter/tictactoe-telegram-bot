package pl.adamboguszewski.tictactoe.server.infrastructure.controller;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;
import pl.adamboguszewski.tictactoe.api.game.response.*;
import pl.adamboguszewski.tictactoe.server.TicTacToeServerApplication;
import pl.adamboguszewski.tictactoe.server.application.GameService;
import pl.adamboguszewski.tictactoe.server.application.dto.MakeAMoveResponseDto;

import javax.validation.Valid;


@RestController
@RequestMapping("/tictactoe/api")
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(TicTacToeServerApplication.class);

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<CreateNewGameResponse> createNewGame(@Valid @RequestBody CreateNewGameRequest request) {
        log.info("Creating new game (controller)");
        // todo
        try {
            return ResponseEntity.ok(new CreateNewGameSuccessResponse(service.createNewGame(request)));
        } catch (Exception e) {
            return new ResponseEntity<>(new CreateNewGameFailureResponse(e.getMessage(), 1L), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{chat_id}")
    public ResponseEntity<MakeAMoveResponse> makeAMove(@Valid @RequestBody MakeAMoveRequest request) {
        log.info("Got make a move request: " + new GsonBuilder().create().toJson(request));
        // todo
        return ResponseEntity.ok(generateMakeAMoveResponseFromDto(service.makeAMove(request)));
    }

    private MakeAMoveResponse generateMakeAMoveResponseFromDto(MakeAMoveResponseDto dto) {
        return new MakeAMoveSuccessResponse(dto.getBoardState(), dto.getStatus(), dto.isXNext());
    }
}
