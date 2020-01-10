package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece implements ChessView.UserChoice {
    public Queen(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.QUEEN, color, chessBoard);
    }

    public List<Move> getMoves(Point pos) {
        List<Move> moves = new ArrayList<Move>();
        ChessBoard chessBoard = this.getChessBoard();

        // Diagonale droite inférieure.
        Move.addMoves(pos, new Point(1, 1), moves, chessBoard);
        // Diagonale droite supérieure.
        Move.addMoves(pos, new Point(1, -1), moves, chessBoard);
        // Diagonale gauche inférieure.
        Move.addMoves(pos, new Point(-1, 1), moves, chessBoard);
        // Diagonale gauche supérieure.
        Move.addMoves(pos, new Point(-1, -1), moves, chessBoard);
        // Ligne bas.
        Move.addMoves(pos, new Point(0, 1), moves, chessBoard);
        // Ligne haut.
        Move.addMoves(pos, new Point(0, -1), moves, chessBoard);
        // Ligne droite.
        Move.addMoves(pos, new Point(1, 0), moves, chessBoard);
        // Ligne gauche.
        Move.addMoves(pos, new Point(-1, 0), moves, chessBoard);

        return moves;
    }

    @Override
    public String textValue() {
        return "Reine";
    }
}
