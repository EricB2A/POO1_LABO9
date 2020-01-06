package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Player owner) {
        super(PieceType.BISHOP, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        return moves;
    }
}
