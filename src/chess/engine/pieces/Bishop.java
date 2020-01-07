package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Player owner) {
        super(PieceType.BISHOP, owner);
    }


    
    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        Board chessBoard = this.getOwner().getBoard();

        // Diagonale inférieure - droite.
        Move.getLine(x, y, 1, 1, moves, chessBoard);
        // Diagonale inférieure - gauche.
        Move.getLine(x, y, 1, -1, moves, chessBoard);
        // Diagonale supérieur - droite
        Move.getLine(x, y, -1, 1, moves, chessBoard);
        // Diagonale supérieure - gauche
        Move.getLine(x, y, -1, -1, moves, chessBoard);

        return moves;
    }
}
