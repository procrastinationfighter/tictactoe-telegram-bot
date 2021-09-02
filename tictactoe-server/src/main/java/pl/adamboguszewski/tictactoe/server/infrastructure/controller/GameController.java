package pl.adamboguszewski.tictactoe.server.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;
import pl.adamboguszewski.tictactoe.api.game.response.CreateNewGameResponse;
import pl.adamboguszewski.tictactoe.api.game.response.CreateNewGameSuccessResponse;
import pl.adamboguszewski.tictactoe.api.game.response.MakeAMoveResponse;
import pl.adamboguszewski.tictactoe.api.game.response.MakeAMoveSuccessResponse;
import pl.adamboguszewski.tictactoe.server.application.GameService;

import javax.validation.Valid;


@RestController
@RequestMapping("/tictactoe/api")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<CreateNewGameResponse> createNewGame(@Valid @RequestBody CreateNewGameRequest request) {
        return ResponseEntity.ok(new CreateNewGameSuccessResponse());
    }

    @PostMapping("/{chat_id}")
    public ResponseEntity<MakeAMoveResponse> makeAMove(@Valid @RequestBody MakeAMoveRequest request) {
        return ResponseEntity.ok(new MakeAMoveSuccessResponse());
    }
}
