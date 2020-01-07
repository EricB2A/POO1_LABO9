package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.Move;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements ChessView.UserChoice {
    public Knight(Player owner) {
        super(PieceType.KNIGHT, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        return moves;
    }

    @Override
    public String textValue() {
        return "Cavalier";
    }
}
