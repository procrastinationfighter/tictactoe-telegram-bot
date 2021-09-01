package pl.adamboguszewski.tictactoe.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.adamboguszewski.tictactoe.api.GameResult;
import pl.adamboguszewski.tictactoe.api.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class TicTacToeServerApplication {

    private static final Logger log = LoggerFactory.getLogger(TicTacToeServerApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(TicTacToeServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ActiveGameRepository gameRepo, FinishedGameRepository finRepo, PlayerRepository playerRepo) {
        return (args) -> {
            log.info("Create new players");
            var player = new Player(2137L, "Papaj");
            var player3 = new Player(1488L, "Akwarelista");
            playerRepo.save(player);
            playerRepo.save(player3);

            log.info("Create a new game");
            List<Tile> tiles = new ArrayList<>();
            tiles.add(Tile.None);
            List<Round> rounds = new ArrayList<>();
            rounds.add(new Round(1L, 0, tiles));

            log.info("Save the game");
            gameRepo.save(new ActiveGame(21L, player, player3, rounds, 33L));
            log.info("Find the game");
            List<ActiveGame> games = gameRepo.findActiveGameByxPlayer(player);
            if (!games.isEmpty()) {
                log.info("Found game");
                log.info("Id:" + games.get(0).getId()
                        + ", XPlayer: " + games.get(0).getxPlayer().getName()
                        + ", OPlayer: " + games.get(0).getoPlayer().getName());
                log.info("XPlayer made a move and won! Changing game to finished...");
                var game = games.get(0);

                log.info("Add the new round");
                rounds.add(new Round(2L, 1, new ArrayList<>(Collections.singleton(Tile.X))));
                log.info("Change rounds");
                game.setRounds(rounds);
                log.info("Add to finished");
                finRepo.save(new FinishedGame(game, GameResult.XWon));
                log.info("Delete active");
                gameRepo.delete(game);

                log.info("Checking if migration was correct");

                if (gameRepo.findActiveGameByxPlayer(player).isEmpty()) {
                    log.info("Correctly deleted the active game");
                } else {
                    log.info("The active game was not deleted");
                }

                var finGame = finRepo.findActiveGameByxPlayer(player);
                if (finGame.isEmpty()) {
                    log.info("Finished game not added");
                } else {
                    var game2 = finGame.get(0);
                    log.info("Correctly added the finished game: " + game.getId());
                }
            } else {
                log.info("Game not found :(");
            }


        };
    }

}
