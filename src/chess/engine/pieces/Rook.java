package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class Rook extends Piece {
    public Rook(Player owner) {
        super(PieceType.ROOK, owner);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
