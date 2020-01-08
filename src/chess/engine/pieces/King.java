package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Player owner, ChessBoard chessBoard) {
        super(PieceType.KING, owner, chessBoard);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();

        Move.addMove(x, y, x+1, y+1, moves, chessBoard);
        Move.addMove(x, y, x+1, 0, moves, chessBoard);
        Move.addMove(x, y, x+1, y-1, moves, chessBoard);

        Move.addMove(x, y, x, y+1, moves, chessBoard);
        Move.addMove(x, y, x, y-1, moves, chessBoard);
        Move.addMove(x, y, x+1, y, moves, chessBoard);
        Move.addMove(x, y, x-1, y, moves, chessBoard);

        Move.addMove(x, y, x-1, y+1, moves, chessBoard);
        Move.addMove(x, y, x-1, y, moves, chessBoard);
        Move.addMove(x, y, x-1, y-1, moves, chessBoard);

        return moves;
    }
}
