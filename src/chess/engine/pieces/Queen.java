package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(Player owner) {
        super(PieceType.QUEEN, owner);
    }

    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        Board chessBoard = this.getOwner().getBoard();

        // Diagonale inférieure - droite.
        Move.addMoves(x, y, 1, 1, moves, chessBoard);
        // Diagonale inférieure - gauche.
        Move.addMoves(x, y, 1, -1, moves, chessBoard);
        // Diagonale supérieur - droite
        Move.addMoves(x, y, -1, 1, moves, chessBoard);
        // Diagonale supérieure - gauche
        Move.addMoves(x, y, -1, -1, moves, chessBoard);
        // Ligne droite.
        Move.addMoves(x, y, 0, 1, moves, chessBoard);
        // Ligne gauche.
        Move.addMoves(x, y, 0, -1, moves, chessBoard);
        // Ligne bas.
        Move.addMoves(x, y, 1, 0, moves, chessBoard);
        // Ligne haut.
        Move.addMoves(x, y, -1, 0, moves, chessBoard);

        return moves;
    }

    
}
