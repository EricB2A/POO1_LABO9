package chess.engine;

public interface Playable {
    boolean checkMove(int fromX, int fromY, int toX, int toY);

    String toString();
}
