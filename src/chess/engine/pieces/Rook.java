package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements ChessView.UserChoice, SpecialFirstMove {
    private boolean hasMoved = false;

    public Rook(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.ROOK, color, chessBoard);
    }
    
    @Override
    public List<Move> getMoves(Point pos) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();

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
        return "Tour";
    }
    public void hasMoved() {
        this.hasMoved = true;
    }
    public boolean hasAlreadyMoved(){
        return !hasMoved;
    }
}
