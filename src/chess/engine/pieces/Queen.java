package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(Player owner) {
        super(PieceType.QUEEN, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        return moves;
    }
}
