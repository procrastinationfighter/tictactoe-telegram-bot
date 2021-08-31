package pl.adamboguszewski.tictactoe.server;

import javax.persistence.*;
import java.util.List;

@Entity
public class ActiveGameStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Player xPlayer;

    @ManyToOne
    private Player oPlayer;

    @OneToMany
    List<Round> rounds;

    private Long chatId;

    public ActiveGameStatus() {
    }

    public ActiveGameStatus(Long id, Player xPlayer, Player oPlayer, List<Round> rounds, Long chatId) {
        this.id = id;
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        this.rounds = rounds;
        this.chatId = chatId;
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
}
