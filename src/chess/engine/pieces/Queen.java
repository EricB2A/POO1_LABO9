package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Piece;
import chess.engine.Playable;
import chess.engine.Player;

public class Queen extends Piece {
    public Queen(Player owner, Board board) {
        super(PieceType.QUEEN, owner, board);
    }

    @Override
    public boolean checkMove( int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
