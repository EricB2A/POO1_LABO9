package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece implements ChessView.UserChoice {
    public Bishop(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.BISHOP, color, chessBoard);
    }

    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<Move>();
        ChessBoard chessBoard = getChessBoard();

        // Diagonale droite inférieure.
        Move.addMoves(pos, new Point(1,1), this, moves, virtual);
        // Diagonale droite supérieure.
        Move.addMoves(pos, new Point(1, -1), this, moves, virtual);
        // Diagonale gauche inférieure.
        Move.addMoves(pos, new Point(-1, 1), this, moves, virtual);
        // Diagonale gauche supérieure.
        Move.addMoves(pos, new Point(-1, -1), this, moves, virtual);

        return moves;
    }

    @Override
    public String textValue() {
        return "Fou";
    }
}
