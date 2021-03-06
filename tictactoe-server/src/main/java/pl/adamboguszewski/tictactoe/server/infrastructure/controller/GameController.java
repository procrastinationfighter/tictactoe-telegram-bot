package pl.adamboguszewski.tictactoe.server.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.adamboguszewski.tictactoe.api.game.PlayerApiEntity;
import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.GetCurrentGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;
import pl.adamboguszewski.tictactoe.api.game.response.*;
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
        try {
            return ResponseEntity.ok(new CreateNewGameSuccessResponse(request.getChatId(), service.createNewGame(request)));
        } catch (Exception e) {
            return new ResponseEntity<>(new CreateNewGameFailureResponse(request.getChatId(), e.getMessage(), 1L),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/move")
    public ResponseEntity<MakeAMoveResponse> makeAMove(@Valid @RequestBody MakeAMoveRequest request) {
        try {
            return ResponseEntity.ok(generateMakeAMoveResponseFromDto(service.makeAMove(request)));
        } catch (Exception e) {
            return new ResponseEntity<>(new MakeAMoveFailureResponse(request.getChatId(), e.getMessage(), 1L),
                    HttpStatus.BAD_REQUEST);
        }
    }

    private MakeAMoveResponse generateMakeAMoveResponseFromDto(MakeAMoveResponseDto dto) {
        return new MakeAMoveSuccessResponse(dto.getChatId(),
                dto.getBoardState(),
                dto.getStatus(),
                new PlayerApiEntity(dto.getxPlayer().getId(), dto.getxPlayer().getName()),
                new PlayerApiEntity(dto.getoPlayer().getId(), dto.getoPlayer().getName()),
                dto.isXNext());
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<GetCurrentGameResponse> getCurrentGame(@Valid @RequestBody GetCurrentGameRequest request, @PathVariable Long chatId) {
        try {
            return ResponseEntity.ok(generateGetCurrentGameResponseFromDto(service.getCurrentGame(request, chatId)));
        } catch (Exception e) {
            return new ResponseEntity<>(new GetCurrentGameFailureResponse(e.getMessage(), 1L),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetCurrentGameResponse generateGetCurrentGameResponseFromDto(GetCurrentGameResponseDto dto) {
        return new GetCurrentGameSuccessResponse(dto.getxPlayerId(), dto.getoPlayerId(), dto.isXNext(), dto.getBoardState());
    }
}
