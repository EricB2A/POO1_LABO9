package chess.engine;

import chess.engine.pieces.Move;

import java.util.List;

public interface Playable {
    List<Move> getMoves(int x, int y);

    String toString();
}
