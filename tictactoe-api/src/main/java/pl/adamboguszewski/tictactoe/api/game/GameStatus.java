package pl.adamboguszewski.tictactoe.api.game;

public enum GameStatus {
    GameActive("active"),
    XWon("X"),
    OWon("O"),
    Draw("draw");

    private final String name;

    GameStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
