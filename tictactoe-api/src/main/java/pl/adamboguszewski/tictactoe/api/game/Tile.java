package pl.adamboguszewski.tictactoe.api.game;

public enum Tile {
    X("X"),
    O("O"),
    None(" ");

    private final String name;

    Tile(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
