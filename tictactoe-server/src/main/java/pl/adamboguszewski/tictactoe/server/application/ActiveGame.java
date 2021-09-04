package pl.adamboguszewski.tictactoe.server.application;

import pl.adamboguszewski.tictactoe.api.game.Tile;

import javax.persistence.*;
import java.util.List;

@Entity
public class ActiveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // [TODO] Add constraint for unique pair (xPlayer, oPlayer)
    @ManyToOne(fetch = FetchType.EAGER)
    private Player xPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player oPlayer;

    @ElementCollection(targetClass = Tile.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Tile> boardState;

    private Long chatId;

    private boolean isXNext;

    // [TODO] Add start time?

    public ActiveGame() {
    }

    public ActiveGame(Player xPlayer, Player oPlayer, List<Tile> boardState, Long chatId) {
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        this.boardState = boardState;
        this.chatId = chatId;
        this.isXNext = true;
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

    public List<Tile> getBoardState() {
        return boardState;
    }

    public void setBoardState(List<Tile> boardState) {
        this.boardState = boardState;
    }

    public boolean isXNext() {
        return isXNext;
    }

    public void setIsXNext(boolean XNext) {
        isXNext = XNext;
    }

    public boolean doesIncludePlayers(Player first, Player second) {
        return first.getId().equals(xPlayer.getId()) && second.getId().equals(oPlayer.getId())
                || first.getId().equals(oPlayer.getId()) && second.getId().equals(xPlayer.getId());
    }
}
