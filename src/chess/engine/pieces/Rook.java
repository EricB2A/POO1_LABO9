package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Move;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Player owner) {
        super(PieceType.ROOK, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        Board chessBoard = this.getOwner().getBoard();

        // Ligne droite.
        Move.getLine(x, y, 0, 1, moves, chessBoard);
        // Ligne gauche.
        Move.getLine(x, y, 0, -1, moves, chessBoard);
        // Ligne bas.
        Move.getLine(x, y, 1, 0, moves, chessBoard);
        // Ligne haut.
        Move.getLine(x, y, -1, 0, moves, chessBoard);
        
        return moves;
    }
}
