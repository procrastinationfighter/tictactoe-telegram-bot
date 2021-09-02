package pl.adamboguszewski.tictactoe.api.game;

public enum GameResult {
    XWon("X"),
    OWon("O"),
    Draw("draw");

    private final String name;

    GameResult(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
