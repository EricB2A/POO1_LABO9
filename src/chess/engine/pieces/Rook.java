package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
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
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();

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
        return "Tour";
    }
    public void hasMoved() {
        this.hasMoved = true;
    }
    public boolean hasAlreadyMoved(){
        return !hasMoved;
    }
}
