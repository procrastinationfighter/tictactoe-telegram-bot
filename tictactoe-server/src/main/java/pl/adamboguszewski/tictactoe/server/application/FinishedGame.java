package pl.adamboguszewski.tictactoe.server.application;

import pl.adamboguszewski.tictactoe.api.GameResult;
import pl.adamboguszewski.tictactoe.api.Tile;

import javax.persistence.*;
import java.util.List;

@Entity
public class FinishedGame {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player xPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player oPlayer;

    @ElementCollection(targetClass = Tile.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Tile> boardState;

    private Long chatId;

    @Enumerated(EnumType.STRING)
    GameResult result;

    public FinishedGame() {
    }

    public FinishedGame(ActiveGame game, GameResult result) {
        this.id = game.getId();
        this.xPlayer = game.getxPlayer();
        this.oPlayer = game.getoPlayer();
        this.boardState = game.getBoardState();
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

    public List<Tile> getBoardState() {
        return boardState;
    }

    public void setBoardState(List<Tile> boardState) {
        this.boardState = boardState;
    }
}
