package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece implements ChessView.UserChoice {
    public Bishop(Player owner) {
        super(PieceType.BISHOP, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        Board chessBoard = this.getOwner().getBoard();

        // Diagonale droite inférieure.
        Move.addMoves(x, y, 1, 1, moves, chessBoard);
        // Diagonale droite supérieure.
        Move.addMoves(x, y, 1, -1, moves, chessBoard);
        // Diagonale gauche inférieure.
        Move.addMoves(x, y, -1, 1, moves, chessBoard);
        // Diagonale gauche supérieure.
        Move.addMoves(x, y, -1, -1, moves, chessBoard);

        return moves;
    }

    @Override
    public String textValue() {
        return "Fou";
    }
}
