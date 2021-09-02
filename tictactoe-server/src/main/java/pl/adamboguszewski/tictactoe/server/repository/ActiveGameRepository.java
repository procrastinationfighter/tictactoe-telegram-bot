package pl.adamboguszewski.tictactoe.server.repository;

import org.springframework.data.repository.CrudRepository;
import pl.adamboguszewski.tictactoe.server.application.ActiveGame;
import pl.adamboguszewski.tictactoe.server.application.Player;

import java.util.List;

public interface ActiveGameRepository extends CrudRepository<ActiveGame, Long> {
    List<ActiveGame> findByChatId(Long chatId);
    List<ActiveGame> findActiveGameByxPlayer(Player player);
}