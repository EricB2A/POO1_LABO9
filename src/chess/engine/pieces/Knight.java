package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements ChessView.UserChoice {
    public Knight(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.KNIGHT, color, chessBoard);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();

        Move.addMove(x + 1, y + 2, this, moves, chessBoard);
        Move.addMove(x - 1, y + 2, this, moves, chessBoard);
        Move.addMove(x + 1, y - 2, this, moves, chessBoard);
        Move.addMove(x - 1, y - 2, this, moves, chessBoard);

        Move.addMove(x + 2, y + 1, this, moves, chessBoard);
        Move.addMove(x - 2, y + 1, this, moves, chessBoard);
        Move.addMove(x + 2, y - 1, this, moves, chessBoard);
        Move.addMove(x - 2, y - 1, this, moves, chessBoard);

        return moves;
    }

    @Override
    public String textValue() {
        return "Cavalier";
    }
}
