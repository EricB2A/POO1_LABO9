package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class King extends Piece {
    public King(Player owner) {
        super(PieceType.KING, owner);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return true;
    }
}
