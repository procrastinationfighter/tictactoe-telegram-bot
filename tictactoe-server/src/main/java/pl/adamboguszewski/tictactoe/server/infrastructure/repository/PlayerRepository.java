package pl.adamboguszewski.tictactoe.server.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;
import pl.adamboguszewski.tictactoe.server.application.Player;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Player> findById(Long id);
}
