package pl.adamboguszewski.tictactoe.server.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;
import pl.adamboguszewski.tictactoe.server.application.FinishedGame;
import pl.adamboguszewski.tictactoe.server.application.Player;

import java.util.List;

public interface FinishedGameRepository extends CrudRepository<FinishedGame, Long> {
    List<FinishedGame> findByChatId(Long chatId);
    List<FinishedGame> findActiveGameByxPlayer(Player player);
}
