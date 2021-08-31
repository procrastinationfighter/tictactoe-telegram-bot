package pl.adamboguszewski.tictactoe.server;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActiveGameRepository extends CrudRepository<ActiveGame, Long> {
    List<ActiveGame> findByChatId(Long chatId);
    List<ActiveGame> findActiveGameByxPlayer(Player player);
}
