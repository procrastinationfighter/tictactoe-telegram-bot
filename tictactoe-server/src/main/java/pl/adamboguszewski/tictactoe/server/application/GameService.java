package pl.adamboguszewski.tictactoe.server.application;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.Tile;
import pl.adamboguszewski.tictactoe.api.game.request.CreateNewGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.GetCurrentGameRequest;
import pl.adamboguszewski.tictactoe.api.game.request.MakeAMoveRequest;
import pl.adamboguszewski.tictactoe.server.TicTacToeServerApplication;
import pl.adamboguszewski.tictactoe.server.application.dto.GetCurrentGameResponseDto;
import pl.adamboguszewski.tictactoe.server.application.dto.MakeAMoveResponseDto;
import pl.adamboguszewski.tictactoe.server.infrastructure.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        log.debug("Got create new game request: " + new GsonBuilder().create().toJson(request));

        Player xPlayer = Player.fromPlayerRequest(request.getXPlayer());
        Player oPlayer = Player.fromPlayerRequest(request.getOPlayer());

        // [fixme] CrudRepository didn't allow me to do it inside the database.
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
        log.debug("Got make a move request for chat " + chatId + ": " + new GsonBuilder().create().toJson(request));

        ActiveGame game = getGameAndValidateMakeAMoveParameters(request, chatId);

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
            log.info("Made a move in the game no. " + game.getId());
            return new MakeAMoveResponseDto(game.getBoardState(), newStatus, game.isXNext());
        } else {
            activeGameRepository.delete(game);
            finishedGameRepository.save(new FinishedGame(game, newStatus));
            log.info("Game no. " + game.getId() + " finished. Result: " + newStatus);
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

    private ActiveGame getActiveGameOrThrowException(Long chatId, Long firstPlayerId, Long secondPlayerId) {
        Optional<Player> firstPlayer = playerRepository.findById(firstPlayerId);
        Optional<Player> secondPlayer = playerRepository.findById(secondPlayerId);

        if (firstPlayer.isEmpty()) {
            throw new RuntimeException("Player with id " + firstPlayerId + " not found.");
        } else if (secondPlayer.isEmpty()) {
            throw new RuntimeException("Player with id " + secondPlayerId + " not found.");
        }

        Optional<ActiveGame> gameOptional = findActiveGame(chatId, firstPlayer.get(), secondPlayer.get());
        if (gameOptional.isEmpty()) {
            throw new RuntimeException("The game with these players does not exist on this chat.");
        } else {
            return gameOptional.get();
        }
    }

    private ActiveGame getGameAndValidateMakeAMoveParameters(MakeAMoveRequest request, Long chatId) {
        ActiveGame game = getActiveGameOrThrowException(chatId, request.getWhoPlayed(), request.getOtherPlayer());

        if (request.getTileNumber() < 0 || request.getTileNumber() >= BOARD_SIZE) {
            throw new RuntimeException("Invalid tile number. Choose between 0 and " + (BOARD_SIZE - 1));
        } else if (!game.isNextPlayerIdCorrect(request.getWhoPlayed())) {
            throw new RuntimeException("It's the other player's turn now.");
        } else if (!game.getBoardState().get(request.getTileNumber()).equals(Tile.None)) {
            throw new RuntimeException("The picked tile is not empty.");
        }

        return game;
    }

    public GetCurrentGameResponseDto getCurrentGame(GetCurrentGameRequest request, Long chatId) {
        log.debug("Got get current game request for chat " + chatId + ": " + new GsonBuilder().create().toJson(request));
        ActiveGame game = getActiveGameOrThrowException(chatId, request.getFirstPlayerId(), request.getSecondPlayerId());
        log.info("Sending state of the game no. " + game.getId());
        return new GetCurrentGameResponseDto(game.getxPlayer().getId(), game.getoPlayer().getId(), game.isXNext(), game.getBoardState());
    }
}
