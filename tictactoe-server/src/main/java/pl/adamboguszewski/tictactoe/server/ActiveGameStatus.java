package pl.adamboguszewski.tictactoe.server;

import pl.adamboguszewski.tictactoe.api.Tile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ActiveGameStatus {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(targetClass = Tile.class)
    @Enumerated(EnumType.STRING)
    private List<Tile> boardState;

    private Long XPlayerId;
    private String XPlayerName;

    private Long OPlayerId;
    private String OPlayerName;

    private Integer roundNumber;
    private Long nextPlayerToMove;

    private Long chatId;

    private LocalDateTime beginDateTime;
    private LocalDateTime lastMoveDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Tile> getBoardState() {
        return boardState;
    }

    public void setBoardState(List<Tile> boardState) {
        this.boardState = boardState;
    }

    public Long getXPlayerId() {
        return XPlayerId;
    }

    public void setXPlayerId(Long XPlayerId) {
        this.XPlayerId = XPlayerId;
    }

    public String getXPlayerName() {
        return XPlayerName;
    }

    public void setXPlayerName(String XPlayerName) {
        this.XPlayerName = XPlayerName;
    }

    public Long getOPlayerId() {
        return OPlayerId;
    }

    public void setOPlayerId(Long OPlayerId) {
        this.OPlayerId = OPlayerId;
    }

    public String getOPlayerName() {
        return OPlayerName;
    }

    public void setOPlayerName(String OPlayerName) {
        this.OPlayerName = OPlayerName;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Long getNextPlayerToMove() {
        return nextPlayerToMove;
    }

    public void setNextPlayerToMove(Long nextPlayerToMove) {
        this.nextPlayerToMove = nextPlayerToMove;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public LocalDateTime getLastMoveDateTime() {
        return lastMoveDateTime;
    }

    public void setLastMoveDateTime(LocalDateTime lastMoveDateTime) {
        this.lastMoveDateTime = lastMoveDateTime;
    }
}
