package chess.engine.pieces;

public class Move {
    private int fromX, fromY, toX, toY;

    public Move(int fromX, int fromY, int toX, int toY){
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public boolean equals(int x, int y){
        return toX == x && toY == y;
    }

    
}
