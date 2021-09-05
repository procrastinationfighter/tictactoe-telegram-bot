package pl.adamboguszewski.tictactoe.server.application;

import pl.adamboguszewski.tictactoe.api.game.GameStatus;
import pl.adamboguszewski.tictactoe.api.game.Tile;

import javax.persistence.*;
import java.util.List;

@Entity
public class ActiveGame {

    private static final int[][] WINNING_POSITIONS = {
            {0, 1, 2}, {0, 3, 6}, {0, 4, 8}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {3, 4, 5}, {6, 7, 8}
    };

    private static int BOARD_SIZE = 9;

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

    public boolean isNextPlayerCorrect(Player player) {
        if (isXNext) {
            return player.getId().equals(xPlayer.getId());
        } else {
            return player.getId().equals(oPlayer.getId());
        }
    }

    public GameStatus getNewStatus() {
        // The game will be finished if one of the players won or all tiles are not empty.
        // Check all possible positions (it's not very effective, but there are only 8 possible winning lines for 3x3)
        for (var position : WINNING_POSITIONS) {
            var state = boardState.get(position[0]);
            if (state.equals(boardState.get(position[1])) && state.equals(boardState.get(position[2]))) {
                if (state.equals(Tile.X)) {
                    return GameStatus.XWon;
                } else if (state.equals(Tile.O)) {
                    return GameStatus.OWon;
                }
                // Else all tiles in a line are empty. Continue.
            }
        }
        // No one has won. The status is either draw or active.

        // Check if not draw.
        int i;
        for (i = 0; i < BOARD_SIZE; i++) {
            if (boardState.get(i).equals(Tile.None)) {
                break;
            }
        }

        if (i == BOARD_SIZE) {
            return GameStatus.Draw;
        } else {
            return GameStatus.GameActive;
        }
    }
}
