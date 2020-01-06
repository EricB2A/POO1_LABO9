package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class Rook extends Piece {
    public Rook(Player owner) {
        super(PieceType.ROOK, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        return true;
    }
}
