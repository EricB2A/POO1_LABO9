package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements ChessView.UserChoice, SpecialFirstMove {
    private boolean hasMoved = false;

    public Rook(Player owner, ChessBoard chessBoard) {
        super(PieceType.ROOK, owner, chessBoard);
    }
    
    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();

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
        return "Tour";
    }
    public void hasMoved() {
        this.hasMoved = true;
    }
    public boolean hasAlreadyMoved(){
        return !hasMoved;
    }
}
