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

    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<Move>();

        // Diagonale droite inférieure.
        Move.addMoves(pos, new Point(1, 1), this, moves, virtual);
        // Diagonale droite supérieure.
        Move.addMoves(pos, new Point(1, -1), this, moves, virtual);
        // Diagonale gauche inférieure.
        Move.addMoves(pos, new Point(-1, 1), this, moves, virtual);
        // Diagonale gauche supérieure.
        Move.addMoves(pos, new Point(-1, -1), this, moves, virtual);
        // Ligne bas.
        Move.addMoves(pos, new Point(0, 1), this, moves, virtual);
        // Ligne haut.
        Move.addMoves(pos, new Point(0, -1), this, moves, virtual);
        // Ligne droite.
        Move.addMoves(pos, new Point(1, 0), this, moves, virtual);
        // Ligne gauche.
        Move.addMoves(pos, new Point(-1, 0), this, moves, virtual);

        return moves;
    }

    @Override
    public String textValue() {
        return "Reine";
    }
}
