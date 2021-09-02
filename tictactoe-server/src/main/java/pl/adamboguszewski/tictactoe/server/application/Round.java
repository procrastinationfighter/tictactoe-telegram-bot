package pl.adamboguszewski.tictactoe.server.application;

import pl.adamboguszewski.tictactoe.api.Tile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Round {
    // Technically, game id and round number could make a primary key.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer roundNumber;

    @ElementCollection(targetClass = Tile.class)
    @Enumerated(EnumType.STRING)
    private List<Tile> boardState;

    private LocalDateTime performedDateTime;

    public Round() {
    }

    public Round(Long id, Integer roundNumber, List<Tile> boardState) {
        this.id = id;
        this.roundNumber = roundNumber;
        this.boardState = boardState;
        this.performedDateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public List<Tile> getBoardState() {
        return boardState;
    }

    public void setBoardState(List<Tile> boardState) {
        this.boardState = boardState;
    }

    public LocalDateTime getPerformedDateTime() {
        return performedDateTime;
    }

    public void setPerformedDateTime(LocalDateTime performedDateTime) {
        this.performedDateTime = performedDateTime;
    }
}
