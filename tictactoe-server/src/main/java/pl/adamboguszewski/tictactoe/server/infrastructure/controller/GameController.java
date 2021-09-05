package pl.adamboguszewski.tictactoe.server.infrastructure.controller;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.GetCurrentGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;
import pl.adamboguszewski.tictactoe.api.game.response.*;
import pl.adamboguszewski.tictactoe.server.TicTacToeServerApplication;
import pl.adamboguszewski.tictactoe.server.application.GameService;
import pl.adamboguszewski.tictactoe.server.application.dto.GetCurrentGameResponseDto;
import pl.adamboguszewski.tictactoe.server.application.dto.MakeAMoveResponseDto;

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
        // todo
        try {
            return ResponseEntity.ok(new CreateNewGameSuccessResponse(service.createNewGame(request)));
        } catch (Exception e) {
            return new ResponseEntity<>(new CreateNewGameFailureResponse(e.getMessage(), 1L), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{chatId}")
    public ResponseEntity<MakeAMoveResponse> makeAMove(@Valid @RequestBody MakeAMoveRequest request, @PathVariable Long chatId) {
        // todo
        try {
            return ResponseEntity.ok(generateMakeAMoveResponseFromDto(service.makeAMove(request, chatId)));
        } catch (Exception e) {
            return new ResponseEntity<>(new MakeAMoveFailureResponse(e.getMessage(), 1L), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private MakeAMoveResponse generateMakeAMoveResponseFromDto(MakeAMoveResponseDto dto) {
        return new MakeAMoveSuccessResponse(dto.getBoardState(), dto.getStatus(), dto.isXNext());
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<GetCurrentGameResponse> getCurrentGame(@Valid @RequestBody GetCurrentGameRequest request, @PathVariable Long chatId) {
        // todo
        try {
            return ResponseEntity.ok(generateGetCurrentGameResponseFromDto(service.getCurrentGame(request, chatId)));
        } catch (Exception e) {
            return new ResponseEntity<>(new GetCurrentGameFailureResponse(e.getMessage(), 1L), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetCurrentGameResponse generateGetCurrentGameResponseFromDto(GetCurrentGameResponseDto dto) {
        return new GetCurrentGameSuccessResponse(dto.getxPlayerId(), dto.getoPlayerId(), dto.isXNext(), dto.getBoardState());
    }
}
