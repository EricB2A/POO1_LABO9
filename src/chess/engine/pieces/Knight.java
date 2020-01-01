package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class Knight extends Piece {
    public Knight(Player owner) {
        super(PieceType.KNIGHT, owner);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return true;
    }
}
