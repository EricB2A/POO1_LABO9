package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Piece;
import chess.engine.Playable;
import chess.engine.Player;

// fou
public class Bishop extends Piece {
    public Bishop(Player owner, Board board) {
        super(PieceType.BISHOP, owner, board);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
