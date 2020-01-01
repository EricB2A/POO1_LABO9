package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class Pawn extends Piece {
    public Pawn(Player owner) {
        super(PieceType.PAWN, owner);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return true;
    }
}
