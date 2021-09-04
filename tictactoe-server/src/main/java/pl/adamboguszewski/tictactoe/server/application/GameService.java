package pl.adamboguszewski.tictactoe.server.application;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.Tile;
import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;
import pl.adamboguszewski.tictactoe.server.TicTacToeServerApplication;
import pl.adamboguszewski.tictactoe.server.application.dto.MakeAMoveResponseDto;
import pl.adamboguszewski.tictactoe.server.infrastructure.repository.ActiveGameRepository;
import pl.adamboguszewski.tictactoe.server.infrastructure.repository.FinishedGameRepository;
import pl.adamboguszewski.tictactoe.server.infrastructure.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class GameService {

    private static final Logger log = LoggerFactory.getLogger(TicTacToeServerApplication.class);

    private static final int BOARD_SIDE = 3;
    private static final int BOARD_SIZE = BOARD_SIDE * BOARD_SIDE;

    private final PlayerRepository playerRepository;
    private final ActiveGameRepository activeGameRepository;
    private final FinishedGameRepository finishedGameRepository;

    public GameService(PlayerRepository playerRepository,
                       ActiveGameRepository activeGameRepository,
                       FinishedGameRepository finishedGameRepository) {
        this.playerRepository = playerRepository;
        this.activeGameRepository = activeGameRepository;
        this.finishedGameRepository = finishedGameRepository;
    }

    public Long createNewGame(CreateNewGameRequest request) {
        log.info("Got create new game request: " + new GsonBuilder().create().toJson(request));

        Player xPlayer = Player.fromPlayerRequest(request.getxPlayer());
        Player oPlayer = Player.fromPlayerRequest(request.getoPlayer());

        // [fixme] CrudRepository didn't allow me to do it inside the database
        List<ActiveGame> activeGames = activeGameRepository.findActiveGamesByChatId(request.getChatId());
        for (var game : activeGames) {
            if (game.doesIncludePlayers(xPlayer, oPlayer)) {
                throw new RuntimeException("The game with these players already exists");
            }
        }

        playerRepository.save(xPlayer);
        playerRepository.save(oPlayer);
        ActiveGame game = new ActiveGame(xPlayer, oPlayer, getEmptyBoard(), request.getChatId());
        game = activeGameRepository.save(game);
        return game.getId();
    }

    public MakeAMoveResponseDto makeAMove(MakeAMoveRequest request) {
        log.info("Got make a move request: " + new GsonBuilder().create().toJson(request));

        return new MakeAMoveResponseDto(new ArrayList<>(), GameStatus.GameActive, true);
    }

    private List<Tile> getEmptyBoard() {
        List<Tile> board = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            board.add(Tile.None);
        }
        return board;
    }
}
