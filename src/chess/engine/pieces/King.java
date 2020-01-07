package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Move;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Player owner) {
        super(PieceType.KING, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        return moves;
    }
}
