package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class King extends Piece {
    public King(Player owner) {
        super(PieceType.KING, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        return list<Move>
    }
}
