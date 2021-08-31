package pl.adamboguszewski.tictactoe.server;

import javax.persistence.*;
import java.util.List;

@Entity
public class FinishedGame {
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player xPlayer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player oPlayer;

    @OneToMany(cascade = CascadeType.ALL)
    List<Round> rounds;

    private Long chatId;

    boolean didXWin;

    public FinishedGame() {
    }

    public FinishedGame(ActiveGame game, boolean didXWin) {
        this.id = game.getId();
        this.xPlayer = game.getxPlayer();
        this.oPlayer = game.getoPlayer();
        this.rounds = game.getRounds();
        this.chatId = game.getChatId();
        this.didXWin = didXWin;
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

    public boolean isDidXWin() {
        return didXWin;
    }

    public void setDidXWin(boolean didXWin) {
        this.didXWin = didXWin;
    }
}
