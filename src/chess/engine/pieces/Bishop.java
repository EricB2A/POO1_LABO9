package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class Bishop extends Piece {
    public Bishop(Player owner) {
        super(PieceType.BISHOP, owner);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
