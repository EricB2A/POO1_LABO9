package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements ChessView.UserChoice {
    public Knight(Player owner, ChessBoard chessBoard) {
        super(PieceType.KNIGHT, owner, chessBoard);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();

        Move.addMove(x, y, x + 1, y + 2, moves, chessBoard);
        Move.addMove(x, y, x - 1, y + 2, moves, chessBoard);
        Move.addMove(x, y, x + 1, y - 2, moves, chessBoard);
        Move.addMove(x, y, x - 1, y - 2, moves, chessBoard);

        Move.addMove(x, y, x + 2, y + 1, moves, chessBoard);
        Move.addMove(x, y, x - 2, y + 1, moves, chessBoard);
        Move.addMove(x, y, x + 2, y - 1, moves, chessBoard);
        Move.addMove(x, y, x - 2, y - 1, moves, chessBoard);

        return moves;
    }

    @Override
    public String textValue() {
        return "Cavalier";
    }
}
