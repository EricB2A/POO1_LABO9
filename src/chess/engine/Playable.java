package chess.engine;

import chess.PlayerColor;

public interface Playable {
    boolean checkMove(int fromX, int fromY, int toX, int toY);

    String toString();

    boolean belongsToPlayer(Player player);
}
