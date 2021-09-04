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
import java.util.Optional;
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
        Optional<ActiveGame> otherGame = findActiveGame(request.getChatId(), xPlayer, oPlayer);
        if (otherGame.isPresent()) {
            throw new RuntimeException("The game with these players already exists");
        }

        playerRepository.save(xPlayer);
        playerRepository.save(oPlayer);
        ActiveGame game = new ActiveGame(xPlayer, oPlayer, getEmptyBoard(), request.getChatId());
        game = activeGameRepository.save(game);

        log.info("Created a new game with id " + game.getId());
        return game.getId();
    }

    public MakeAMoveResponseDto makeAMove(MakeAMoveRequest request, Long chatId) {
        log.info("Got make a move request for chat " + chatId + ": " + new GsonBuilder().create().toJson(request));

        // todo reformat
        Player whoPlayed = Player.fromPlayerRequest(request.getWhoPlayed());
        Player otherPlayer = Player.fromPlayerRequest(request.getOtherPlayer());

        Optional<ActiveGame> gameOptional = findActiveGame(request.getChatId(), whoPlayed, otherPlayer);
        if (gameOptional.isEmpty()) {
            throw new RuntimeException("The game with these players does not exist on this chat.");
        } else if (request.getTileNumber() < 0 || request.getTileNumber() >= BOARD_SIZE) {
            throw new RuntimeException("Invalid tile number. Choose between 0 and " + (BOARD_SIZE - 1));
        }

        ActiveGame game = gameOptional.get();

        if (game.isNextPlayerCorrect(whoPlayed)) {
            throw new RuntimeException("It's the other player's turn now.");
        } else if (!game.getBoardState().get(request.getTileNumber()).equals(Tile.None)) {
            throw new RuntimeException("The picked tile is not empty.");
        }

        if (game.isXNext()) {
            game.getBoardState().set(request.getTileNumber(), Tile.X);
        } else {
            game.getBoardState().set(request.getTileNumber(), Tile.O);
        }
        // Change the next player.
        game.setIsXNext(!game.isXNext());

        GameStatus newStatus = game.getNewStatus();

        if (newStatus.equals(GameStatus.GameActive)) {
            activeGameRepository.save(game);
            return new MakeAMoveResponseDto(game.getBoardState(), newStatus, game.isXNext());
        } else {
            activeGameRepository.delete(game);
            finishedGameRepository.save(new FinishedGame(game, newStatus));
            return new MakeAMoveResponseDto(game.getBoardState(), newStatus, false);
        }
    }

    private List<Tile> getEmptyBoard() {
        List<Tile> board = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            board.add(Tile.None);
        }
        return board;
    }

    private Optional<ActiveGame> findActiveGame(Long chatId, Player first, Player second) {
        List<ActiveGame> activeGames = activeGameRepository.findActiveGamesByChatId(chatId);
        for (var game : activeGames) {
            if (game.doesIncludePlayers(first, second)) {
                return Optional.of(game);
            }
        }

        return Optional.empty();
    }
}
