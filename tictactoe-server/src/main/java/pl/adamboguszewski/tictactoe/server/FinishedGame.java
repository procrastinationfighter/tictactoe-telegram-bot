package pl.adamboguszewski.tictactoe.server;

import pl.adamboguszewski.tictactoe.api.GameResult;

import javax.persistence.*;
import java.util.List;

@Entity
public class FinishedGame {
    @Id
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Player xPlayer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Player oPlayer;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<Round> rounds;

    private Long chatId;

    GameResult result;

    public FinishedGame() {
    }

    public FinishedGame(ActiveGame game, GameResult result) {
        this.id = game.getId();
        this.xPlayer = game.getxPlayer();
        this.oPlayer = game.getoPlayer();
        this.rounds = game.getRounds();
        this.chatId = game.getChatId();
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getxPlayer() {
        return xPlayer;
    }

    public void setxPlayer(Player xPlayer) {
        this.xPlayer = xPlayer;
    }

    public Player getoPlayer() {
        return oPlayer;
    }

    public void setoPlayer(Player oPlayer) {
        this.oPlayer = oPlayer;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}
