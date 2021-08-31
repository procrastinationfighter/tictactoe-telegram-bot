package pl.adamboguszewski.tictactoe.server;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FinishedGameRepository extends CrudRepository<FinishedGame, Long> {
    List<FinishedGame> findByChatId(Long chatId);
    List<FinishedGame> findActiveGameByxPlayer(Player player);
}
