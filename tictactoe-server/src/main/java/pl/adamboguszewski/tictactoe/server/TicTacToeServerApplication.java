package pl.adamboguszewski.tictactoe.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.adamboguszewski.tictactoe.api.Tile;
import pl.adamboguszewski.tictactoe.server.application.ActiveGame;
import pl.adamboguszewski.tictactoe.server.application.Player;
import pl.adamboguszewski.tictactoe.server.repository.ActiveGameRepository;
import pl.adamboguszewski.tictactoe.server.repository.FinishedGameRepository;
import pl.adamboguszewski.tictactoe.server.repository.PlayerRepository;

import java.util.ArrayList;
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
            playerRepo.save(player3);
            playerRepo.save(player);

            log.info("Create a new game");
            List<Tile> tiles = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                tiles.add(Tile.None);
            }

            var game = gameRepo.save(new ActiveGame(player, player3, tiles, 21L));
            log.info("Check if added");
            var found = gameRepo.findByChatId(21L);
            if (found.isEmpty()) {
                log.info("Adding failed");
            } else {
                log.info("Adding successful");
                log.info("Try adding another with one common player");

                var player2 = new Player(42L, "Kubica");
                var player4 = new Player(2137L, "Papajak");
                playerRepo.save(player2);
                playerRepo.save(player4);

                var game2 = gameRepo.save(new ActiveGame(player2, player4, tiles, 21L));
                log.info("Added");
                found = gameRepo.findByChatId(21L);
                if (found.size() != 2) {
                    log.info("Wrong number of games");
                } else {
                    log.info("OK");
                }


//                found.get(0).getBoardState().set(4, Tile.X);
//                gameRepo.save(found.get(0));
//
//                log.info("Added, now check if ok");
//                found = gameRepo.findByChatId(21L);
//                log.info("Board state: ");
//                for (var tile : found.get(0).getBoardState()) {
//                    log.info(tile.toString());
//                }
//
//                log.info("Try finishing the game");
//                finRepo.save(new FinishedGame(found.get(0), GameResult.Draw));
//                log.info("Added finished game");
//                var finFound = finRepo.findByChatId(21L);
//                if (finFound.isEmpty()) {
//                    log.info("Added failed");
//                } else {
//                    log.info("Added successful, now try to remove old game");
//                    gameRepo.delete(found.get(0));
//                    log.info("Deleted, check if success");
//                    found = gameRepo.findByChatId(21L);
//                    if (found.isEmpty()) {
//                        log.info("success");
//                    } else {
//                        log.info("fail");
//                    }
//                }
            }

        };
    }

}
