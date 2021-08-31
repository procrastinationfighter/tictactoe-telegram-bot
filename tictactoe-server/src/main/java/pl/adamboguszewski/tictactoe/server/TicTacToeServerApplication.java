package pl.adamboguszewski.tictactoe.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.adamboguszewski.tictactoe.api.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class TicTacToeServerApplication {

    private static final Logger log = LoggerFactory.getLogger(TicTacToeServerApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(TicTacToeServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerRepository playerRepo, ActiveGameRepository gameRepo) {
        return (args) -> {
            // save a few customers
            playerRepo.save(new Player(2137L, "Papaj"));
            playerRepo.save(new Player(1488L, "Akwarelista"));
            playerRepo.save(new Player(42L, "Kubica"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Player player : playerRepo.findAll()) {
                log.info("Got: " + player.getName());
            }
            log.info("");

            // fetch an individual customer by ID
            Optional<Player> player = playerRepo.findById(2137L);
            if (player.isPresent()) {
                log.info("Player found with findById(2137L):");
                log.info("--------------------------------");
                log.info(player.get().getName());
                log.info("");
            } else {
                log.info("Player with id 2137L not found");
            }

            // fetch an individual customer by ID
            Optional<Player> player2 = playerRepo.findById(22L);
            if (player2.isPresent()) {
                log.info("Player found with findById(2137L):");
                log.info("--------------------------------");
                log.info(player2.get().getName());
                log.info("");
            } else {
                log.info("Player with id 22L not found");
            }
            List<Tile> tiles = new ArrayList<>();
            tiles.add(Tile.None);
            List<Round> rounds = new ArrayList<>();
            rounds.add(new Round(1L, 0, tiles));

            Optional<Player> player3 = playerRepo.findById(1488L);
            if (player.isPresent() && player3.isPresent()) {
                gameRepo.save(new ActiveGame(21L, player.get(), player3.get(), rounds, 33L));
                List<ActiveGame> games = gameRepo.findActiveGameByxPlayer(player.get());
                if (!games.isEmpty()) {
                    log.info("Found game");
                    log.info("Id:" + games.get(0).getId()
                            + ", XPlayer: " + games.get(0).getxPlayer().getName()
                            + ", OPlayer: " + games.get(0).getoPlayer().getName());
                } else {
                    log.info("Game not found :(");
                }
            } else {
                log.info("One of the players not found");
            }



        };
    }

}
