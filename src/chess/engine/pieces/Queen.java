package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece implements ChessView.UserChoice {
    public Queen(Player owner) {
        super(PieceType.QUEEN, owner);
    }

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
        // Ligne bas.
        Move.addMoves(x, y, 0, 1, moves, chessBoard);
        // Ligne haut.
        Move.addMoves(x, y, 0, -1, moves, chessBoard);
        // Ligne droite.
        Move.addMoves(x, y, 1, 0, moves, chessBoard);
        // Ligne gauche.
        Move.addMoves(x, y, -1, 0, moves, chessBoard);

        return moves;
    }

    @Override
    public String textValue() {
        return "Reine";
    }
}
