package pl.adamboguszewski.tictactoe.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class TicTacToeServerApplication {

    private static final Logger log = LoggerFactory.getLogger(TicTacToeServerApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(TicTacToeServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new Player(2137L, "Papaj"));
            repository.save(new Player(1488L, "Akwarelista"));
            repository.save(new Player(42L, "Kubica"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Player player : repository.findAll()) {
                log.info("Got: " + player.getName());
            }
            log.info("");

            // fetch an individual customer by ID
            Optional<Player> player = repository.findById(2137L);
            if (player.isPresent()) {
                log.info("Player found with findById(2137L):");
                log.info("--------------------------------");
                log.info(player.get().getName());
                log.info("");
            } else {
                log.info("Player with id 2137L not found");
            }

            // fetch an individual customer by ID
            Optional<Player> player2 = repository.findById(22L);
            if (player2.isPresent()) {
                log.info("Player found with findById(2137L):");
                log.info("--------------------------------");
                log.info(player2.get().getName());
                log.info("");
            } else {
                log.info("Player with id 22L not found");
            }
        };
    }

}
