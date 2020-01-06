package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Piece;
import chess.engine.Playable;
import chess.engine.Player;

public class
Rook extends Piece {
    public Rook(Player owner, Board board) {
        super(PieceType.ROOK, owner, board);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
