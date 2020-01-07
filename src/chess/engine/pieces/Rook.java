package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.Board;
import chess.engine.Move;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements ChessView.UserChoice {
    public Rook(Player owner) {
        super(PieceType.ROOK, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        Board chessBoard = this.getOwner().getBoard();

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
}
