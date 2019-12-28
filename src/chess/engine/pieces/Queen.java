package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

public class Queen extends Piece {
    public Queen(Player owner) {
        super(PieceType.QUEEN, owner);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
